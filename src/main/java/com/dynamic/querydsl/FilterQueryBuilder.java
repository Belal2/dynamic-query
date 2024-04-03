package com.dynamic.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.Map;
public class FilterQueryBuilder {

    public static <T,E extends Enum<E> & FieldEnum> Predicate buildPredicate(Map<String, Object> filters, Class<E> enumClass, Class<T> entityClass) {
        PathBuilder<T> path = new PathBuilder<>(entityClass, entityClass.getSimpleName().toLowerCase());
        BooleanExpression result = Expressions.asBoolean(true).isTrue();

        for (E fieldEnum : enumClass.getEnumConstants()) {
            Object filterValue = filters.get(fieldEnum.getFieldName());
            if (filterValue != null) {
                BooleanExpression expression = fieldEnum.getPredicateBuilder().build(path,filterValue);
                result = result == null ? expression : result.and(expression);
            }
        }
        return result;
    }


    public static <E extends Enum<E> & FieldEnum> Predicate buildPredicate(Map<String, Object> filters, Class<E> enumClass) {
        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (E fieldEnum : enumClass.getEnumConstants()) {
            Object filterValue = filters.get(fieldEnum.getFieldName());
            if (filterValue != null) {
                BooleanExpression expression = fieldEnum.getPredicateFactory().apply(filterValue);
                result = result == null ? expression : result.and(expression);
            }
        }
        return result;
    }

}
