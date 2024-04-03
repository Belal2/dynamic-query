package com.dynamic.querylogging;

import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.ExecutionInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class LogQueries {
    private final DataSource dataSource;

    public LogQueries(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void logQueryInDatabase(ExecutionInfo execInfo, String query, int querySize, String params) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "insert into query_log ( connection, time, success, type, patch, query_size, patch_size, query,params) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, execInfo.getConnectionId());
                preparedStatement.setLong(2, execInfo.getElapsedTime());
                preparedStatement.setBoolean(3, execInfo.isSuccess());
                preparedStatement.setString(4, String.valueOf(execInfo.getStatementType()));
                preparedStatement.setBoolean(5, execInfo.isBatch());
                preparedStatement.setInt(6, querySize);
                preparedStatement.setInt(7, execInfo.getBatchSize());
                preparedStatement.setString(8, query);
                preparedStatement.setString(9, params);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Failed to execute log query", e);
        }
    }


    private void upsertogQueryInDatabase(ExecutionInfo execInfo, String query, int querySize, String params) {
        String upsertSql = "INSERT INTO query_log (connection, time, success, type, patch, query_size, patch_size, query, params) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " + "ON CONFLICT (query) " + "DO UPDATE SET time = ?, success = ?, type = ?, patch = ?, query_size = ?, patch_size = ?, params = ?;";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement upsertStatement = connection.prepareStatement(upsertSql)) {
                upsertStatement.setString(1, execInfo.getConnectionId());
                upsertStatement.setLong(2, execInfo.getElapsedTime());
                upsertStatement.setBoolean(3, execInfo.isSuccess());
                upsertStatement.setString(4, String.valueOf(execInfo.getStatementType()));
                upsertStatement.setBoolean(5, execInfo.isBatch());
                upsertStatement.setInt(6, querySize);
                upsertStatement.setInt(7, execInfo.getBatchSize());
                upsertStatement.setString(8, query);
                upsertStatement.setString(9, params);
                // Set update values
                upsertStatement.setLong(10, execInfo.getElapsedTime());
                upsertStatement.setBoolean(11, execInfo.isSuccess());
                upsertStatement.setString(12, String.valueOf(execInfo.getStatementType()));
                upsertStatement.setBoolean(13, execInfo.isBatch());
                upsertStatement.setInt(14, querySize);
                upsertStatement.setInt(15, execInfo.getBatchSize());
                upsertStatement.setString(16, params);
                upsertStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
