package com.dynamic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "departments", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "department_name", length = 30, nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @JsonBackReference
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
    @JsonBackReference
    private Employee manager;


}
