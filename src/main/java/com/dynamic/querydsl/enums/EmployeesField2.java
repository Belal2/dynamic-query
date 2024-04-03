package com.dynamic.querydsl.enums;

import com.dynamic.querydsl.interfaces.ServiceEmployeesInterfaces;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.function.Function;

public enum EmployeesField2 implements ServiceEmployeesInterfaces {
    EMPLOYEE_ID("employeeId", ServiceEmployeesInterfaces.longPredicateFactory("employeeId")),
    FIRST_NAME("firstName", ServiceEmployeesInterfaces.stringPredicateFactory("firstName")),
    LAST_NAME("lastName", ServiceEmployeesInterfaces.stringPredicateFactory("lastName")),
    EMAIL("email", ServiceEmployeesInterfaces.stringPredicateFactory("email")),
    PHONE_NUMBER("phoneNumber", ServiceEmployeesInterfaces.stringPredicateFactory("phoneNumber")),
    HIRE_DATE("hireDate", ServiceEmployeesInterfaces.dateRangePredicateFactory("hireDate")),
    SALARY("salary", ServiceEmployeesInterfaces.bigDecimalPredicateFactory("salary")),
    DEPARTMENT("department", ServiceEmployeesInterfaces.listPredicateFactory("department"));



    private final String fieldName;
    private final ServiceEmployeesInterfaces.PredicateBuilder predicateBuilder;

    EmployeesField2(String fieldName, ServiceEmployeesInterfaces.PredicateBuilder predicateBuilder) {
        this.fieldName = fieldName;
        this.predicateBuilder = predicateBuilder;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Function<Object, BooleanExpression> getPredicateFactory() {
        return null;
    }

    @Override
    public ServiceEmployeesInterfaces.PredicateBuilder getPredicateBuilder() {
        return predicateBuilder;
    }

}
