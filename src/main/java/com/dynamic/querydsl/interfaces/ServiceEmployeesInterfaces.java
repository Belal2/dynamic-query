package com.dynamic.querydsl.interfaces;

import com.dynamic.querydsl.FieldEnum;
import com.querydsl.core.types.dsl.Expressions;

import java.math.BigDecimal;
import java.time.Instant;

public interface ServiceEmployeesInterfaces extends FieldEnum {
    static FieldEnum.PredicateBuilder stringPredicateFactory(String fieldName) {
        return (pathBuilder, value) -> pathBuilder.getString(fieldName).eq((String) value);
    }

    static PredicateBuilder longPredicateFactory(String fieldName) {
        return (pathBuilder, value) -> pathBuilder.getNumber(fieldName, Long.class).eq((Long) value);
    }

    static PredicateBuilder bigDecimalPredicateFactory(String fieldName) {
        return (pathBuilder, value) -> pathBuilder.getNumber(fieldName, BigDecimal.class).eq((BigDecimal) value);
    }

    static PredicateBuilder instantPredicateFactory(String fieldName) {
        return (pathBuilder, value) -> pathBuilder.getDateTime(fieldName, Instant.class).eq((Instant) value);
    }

    static PredicateBuilder dateRangePredicateFactory(String fieldName) {
        return (pathBuilder, value) -> {
            if (value instanceof Object[] values) {
                if (values.length == 2 && values[0] instanceof Instant && values[1] instanceof Instant) {
                    return pathBuilder.getDateTime(fieldName, Instant.class).between((Instant) values[0], (Instant) values[1]);
                }
            }else
                if(value instanceof Instant){
                return    pathBuilder.getDateTime(fieldName, Instant.class).eq((Instant) value);
            }
            return Expressions.asBoolean(false);
        };
    }

    static PredicateBuilder listPredicateFactory(String fieldName) {
        return (pathBuilder, value) -> pathBuilder.getList(fieldName, Object.class).contains(value);
    }



}