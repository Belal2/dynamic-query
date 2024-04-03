package com.dynamic.querydsl.enums;

import com.dynamic.entity.QEmployee;
import com.dynamic.querydsl.FieldEnum;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.function.Function;

public enum EmployeesField implements FieldEnum {

    EMPLOYEE_ID("employeeId", value -> QEmployee.employee.employeeId.eq((Long) value)),
    FIRST_NAME("firstName", value -> QEmployee.employee.firstName.eq((String) value)),
    LAST_NAME("lastName", value -> QEmployee.employee.lastName.eq((String) value)),
    EMAIL("email", value -> QEmployee.employee.email.eq((String) value)),
    PHONE_NUMBER("phoneNumber", value -> QEmployee.employee.phoneNumber.eq((String) value)),
    HIRE_DATE("hireDate", value -> QEmployee.employee.hireDate.eq((Instant) value)),
    SALARY("salary", value -> QEmployee.employee.salary.eq((BigDecimal) value)),
    COMMISSION_PCT("commissionPct", value -> QEmployee.employee.commissionPct.eq((BigDecimal) value));

    private final String fieldName;
    private final Function<Object, BooleanExpression> predicateFactory;

    EmployeesField(String fieldName, Function<Object, BooleanExpression> predicateFactory) {
        this.fieldName = fieldName;
        this.predicateFactory = predicateFactory;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Function<Object, BooleanExpression> getPredicateFactory() {
        return predicateFactory;
    }

    @Override
    public PredicateBuilder getPredicateBuilder() {
        return null;
    }
}