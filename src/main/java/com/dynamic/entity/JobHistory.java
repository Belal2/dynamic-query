package com.dynamic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "job_history", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
public class JobHistory {

    @Id
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Id
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @JsonBackReference

    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @JsonBackReference
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", referencedColumnName = "job_id")
    @JsonBackReference
    private Job job;



}
