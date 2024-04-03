package com.dynamic.querydsl.services;

import com.dynamic.dto.RequestDto;
import com.dynamic.entity.Employee;
import com.dynamic.entity.QEmployee;
import com.dynamic.querydsl.FilterQueryBuilder;
import com.dynamic.querydsl.enums.EmployeesField;
import com.dynamic.querydsl.enums.EmployeesField2;
import com.dynamic.querydsl.mapper.EmployeeMapper;
import com.dynamic.querydsl.repositories.EmployeeRepositoryByQueryDsl;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SearchInEmployee {
    private final EmployeeRepositoryByQueryDsl employeeRepositoryByQueryDsl;

    public List<Employee> filteredEmployeeFail(RequestDto request) {
        Map<String, Object> filters = EmployeeMapper.convertDtoToMap2(request);
        Predicate predicate = FilterQueryBuilder.buildPredicate(filters, EmployeesField2.class, Employee.class);
        Iterable<Employee> iter;
        iter = employeeRepositoryByQueryDsl.findAll(predicate);
        return StreamSupport.stream(iter.spliterator(), false).toList();

    }

    public List<Employee> filteredEmployee(RequestDto request) {
        Map<String, Object> filters = EmployeeMapper.convertDtoToMap(request);
        Predicate predicate = FilterQueryBuilder.buildPredicate(filters, EmployeesField.class);
        Iterable<Employee> iter;
        iter = employeeRepositoryByQueryDsl.findAll(predicate);
        return StreamSupport.stream(iter.spliterator(), false).toList();
    }


    //using binding
    public List<Employee> searchEmployees(String keyword) {
        return (List<Employee>) employeeRepositoryByQueryDsl.findAll(QEmployee.employee.firstName.containsIgnoreCase(keyword)
                .or(QEmployee.employee.lastName.containsIgnoreCase(keyword))
                .or(QEmployee.employee.email.containsIgnoreCase(keyword)));
    }

}
