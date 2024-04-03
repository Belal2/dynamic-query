package com.dynamic.querydsl.services;


import com.dynamic.entity.Employee;
import com.dynamic.querydsl.repositories.EmployeeRepositoryByQueryDsl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepositoryByQueryDsl employeeRepositoryByQueryDsl;

    public Iterable<Employee> findAll() {
        return employeeRepositoryByQueryDsl.findAll();
    }

    public Employee findManagerByEmployeeId(Long empId)
    {
       return employeeRepositoryByQueryDsl.findManagerByEmployeeId(empId);
    }

    public Employee getDeparrtmentByEmpId(Long empId)
    {
        return employeeRepositoryByQueryDsl.findById(empId).get().getDepartment().getManager();
    }
}
