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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "countryId")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class Country {

    @Id
    @Column(name = "country_id", length = 2, nullable = false)
    private String countryId;

    @Column(name = "country_name", length = 40)
    private String countryName;

//    @Column(name = "region_id")
//    private Integer countryId;

    @OneToMany(mappedBy = "country")
    @JsonManagedReference
    private List<Location> locations;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    @JsonBackReference
    private Region region;

}
