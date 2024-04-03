package com.dynamic.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.function.Function;

public interface  FieldEnum {
    String getFieldName();

    Function<Object, BooleanExpression> getPredicateFactory();

    PredicateBuilder getPredicateBuilder();

    interface PredicateBuilder {
        BooleanExpression build(PathBuilder<?> pathBuilder, Object value);
    }
}
