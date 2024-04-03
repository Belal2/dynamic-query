package com.dynamic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "countries", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
public class Country {

    @Id
    @Column(name = "country_id", length = 2, nullable = false)
    private String countryId;

    @Column(name = "country_name", length = 40)
    private String countryName;

    @OneToMany(mappedBy = "country")
    @JsonManagedReference
    private List<Location> locations;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    @JsonBackReference
    private Region region;

}
