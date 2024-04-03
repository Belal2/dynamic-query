package com.dynamic.querydsl.mapper;

import com.dynamic.dto.RequestDto;
import com.dynamic.querydsl.enums.EmployeesField;
import com.dynamic.querydsl.enums.EmployeesField2;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeMapper {

    private static Instant getInstantFromLocalDate(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    private static Instant getInstantFromLocalDateWithTime(LocalDate localDate, int hour, int minute) {
        LocalDateTime localDateTime = localDate.atTime(hour, minute);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    public static Map<String, Object> convertDtoToMap2(RequestDto requestDto) {
        Map<String, Object> map = new HashMap<>();
        if(requestDto.getEmpId() != null) {
            map.put(EmployeesField2.EMPLOYEE_ID.getFieldName(), requestDto.getEmpId());
        }
        if(requestDto.getEmpName() != null) {
            map.put(EmployeesField2.FIRST_NAME.getFieldName(), requestDto.getEmpName());
        }
        if(requestDto.getStartHireDate() != null && requestDto.getEndHireDate() != null) {
            map.put(EmployeesField2.HIRE_DATE.getFieldName(), List.of(getInstantFromLocalDate(requestDto.getStartHireDate()), getInstantFromLocalDate(requestDto.getEndHireDate())));
        }
        return map;
    }

    public static Map<String, Object> convertDtoToMap(RequestDto requestDto) {
        Map<String, Object> map = new HashMap<>();
        if(requestDto.getEmpId() != null) {
            map.put(EmployeesField.EMPLOYEE_ID.getFieldName(), requestDto.getEmpId());
        }
        if(requestDto.getEmpName() != null) {
            map.put(EmployeesField.FIRST_NAME.getFieldName(), requestDto.getEmpName());
        }
        if(requestDto.getStartHireDate() != null && requestDto.getEndHireDate() != null) {
            map.put(EmployeesField.HIRE_DATE.getFieldName(), List.of(getInstantFromLocalDate(requestDto.getStartHireDate()), getInstantFromLocalDate(requestDto.getEndHireDate())));
        }
        return map;
    }
}
