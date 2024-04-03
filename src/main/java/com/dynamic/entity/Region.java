package com.dynamic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "regions", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "region_name", length = 25)
    private String regionName;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Country> countries;
}
