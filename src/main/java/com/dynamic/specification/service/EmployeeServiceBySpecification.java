package com.dynamic.specification.service;

import com.dynamic.dto.FilterDTO;
import com.dynamic.entity.Employee;
import com.dynamic.specification.repo.EmployeeRepository;
import com.dynamic.specification.service.specifications.EmployeeSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceBySpecification {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSpecifications employeeSpecifications;

    public Page<Employee> getEmployeeByFilter(List<FilterDTO> filterDTOList, int page, int size) {

        Pageable pageable = PageRequest.of(page , size);
        return employeeRepository.findAll(employeeSpecifications.getSpecificationFromFilters(filterDTOList),
                pageable);

    }

}
