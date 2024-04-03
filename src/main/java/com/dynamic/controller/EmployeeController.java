package com.dynamic.controller;

import com.dynamic.dto.FilterDTO;
import com.dynamic.dto.RequestDto;
import com.dynamic.entity.Employee;
import com.dynamic.querydsl.services.SearchInEmployee;
import com.dynamic.specification.service.EmployeeByCriteria;
import com.dynamic.specification.service.EmployeeServiceBySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
private final EmployeeServiceBySpecification employeeServiceBySpecification;
private final EmployeeByCriteria employeeServicecriteria;
    private final SearchInEmployee searchInEmployee;


    @PostMapping("/request")
    public Page<Employee> findByRequest(@RequestBody List<FilterDTO> filterDTOList, @RequestParam int page,@RequestParam int size) {
        return employeeServiceBySpecification.getEmployeeByFilter(filterDTOList, page, size);
    }

    @PostMapping("/criteria")
    public List<Employee> findByCriteria(@RequestBody List<FilterDTO> filterDTOList, @RequestParam int page,@RequestParam int size) {
        return employeeServicecriteria.findEmployeesByCriteria(filterDTOList.getFirst().getField(),filterDTOList.getFirst().getValue());
    }

    @PostMapping("/request/querydsl")
    public List<Employee> findByRequest(@RequestBody RequestDto requestDto) {
        return searchInEmployee.filteredEmployee(requestDto);
    }


}
