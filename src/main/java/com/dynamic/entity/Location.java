package com.dynamic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "locations", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "street_address", length = 40)
    private String streetAddress;

    @Column(name = "postal_code", length = 12)
    private String postalCode;

    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @Column(name = "state_province", length = 25)
    private String stateProvince;

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Department> departments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @JsonBackReference
    private Country country;

}