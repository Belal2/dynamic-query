package com.dynamic.dto;

import com.dynamic.specification.enums.QueryOperator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterDTO {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<String> values;

}
