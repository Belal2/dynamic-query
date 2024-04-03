package com.dynamic.querylogging;

import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.Log4jSlowQueryListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class QueryLogConfig {
    @Bean
    public DataSource getDataSource(DataSourceProperties dataSourceProperties) {
        Formatter formatter = FormatStyle.BASIC.getFormatter();
        DataSource originalDatasource = dataSourceProperties.initializeDataSourceBuilder().build();
        Log4jSlowQueryListener log4jSlowQueryListener = new Log4jSlowQueryListener(1, TimeUnit.MINUTES) {
            @Override
            protected String getExecutionInfoKey(ExecutionInfo executionInfo) {
                System.err.println("EEEEEE");
                return super.getExecutionInfoKey(executionInfo);
            }
        };
        SLF4JQueryLoggingListener listener = new SLF4JQueryLoggingListener() {
            @Override
            public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
                String query = queryInfoList.stream().map(QueryInfo::getQuery).collect(Collectors.joining("\n"));
                String parametersString = getParamsFromQuery(queryInfoList);
                if (!parametersString.isEmpty()) {
                    parametersString = parametersString.substring(0, parametersString.length() - 2);
                }
                super.afterQuery(execInfo, queryInfoList);
                // remove excludedTables list and this if when you want to use log file only without log in db
//                List<String> excludedTables = new ArrayList<>();
//                excludedTables.add("query_log");
//                if (!query.isEmpty() && excludedTables.stream().noneMatch(table -> query.toLowerCase().contains(table.toLowerCase()))) {
//                    LogQueries logQueries = new LogQueries(originalDatasource);
//                    logQueries.logQueryInDatabase(execInfo, query, queryInfoList.size(), parametersString);
//                }
            }
        };
        return ProxyDataSourceBuilder.create(originalDatasource).name("Proxy DataSource").formatQuery(formatter::format).logQueryBySlf4j(SLF4JLogLevel.INFO).listener(listener).listener(log4jSlowQueryListener).multiline().asJson().build();
    }

    private String getParamsFromQuery(List<QueryInfo> queryInfoList) {
        return queryInfoList.stream().flatMap(queryInfo -> queryInfo.getParametersList().stream()).flatMap(List::stream).peek(param -> {
            for (int i = 0; i < param.getArgs().length; i++) {
                if (i % 2 == 0) param.getArgs()[i] = param.getArgs()[i] + ": ";
                else param.getArgs()[i] = param.getArgs()[i] + "\n ";
            }
        }).flatMap(operation -> Stream.of(operation.getArgs())).map(String::valueOf).collect(Collectors.joining());

    }


}
