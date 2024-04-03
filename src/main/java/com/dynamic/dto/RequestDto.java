package com.dynamic.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    private String empName;
    private Long empId;
    private LocalDate startHireDate;
    private LocalDate endHireDate;
}
