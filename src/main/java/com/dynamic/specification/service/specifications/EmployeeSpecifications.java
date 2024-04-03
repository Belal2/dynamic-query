package com.dynamic.specification.service.specifications;

import com.dynamic.dto.FilterDTO;
import com.dynamic.entity.Employee;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class EmployeeSpecifications {
    //    public static Specification<Employee> columnEqual(List<FilterDTO> filterDTOList) {
//            return (root, query, criteriaBuilder) -> {
//                List<Predicate> predicates = new ArrayList<>();
//                for (FilterDTO filter : filterDTOList) {
//                    Predicate predicate = criteriaBuilder.equal(root.get(filter.getColumnName()), filter.getColumnValue());
//                    predicates.add(predicate);
//                }
//                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//            };
//        }
    public Specification<Employee> getSpecificationFromFilters(List<FilterDTO> filter) {
        Specification<Employee> specification = where(createSpecification(filter.getFirst()));
        filter.removeFirst(); // remove
        for (FilterDTO input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    public Specification<Employee> createSpecification(FilterDTO input) {
        return (root, query, criteriaBuilder) -> {
            Path attribute = getAttribute(root, input.getField());
            return switch (input.getOperator()) {
                case EQUALS ->
                        criteriaBuilder.equal(attribute, castToRequiredType(attribute.getJavaType(), input.getValue()));
                case NOT_EQ ->
                        criteriaBuilder.notEqual(attribute, castToRequiredType(attribute.getJavaType(), input.getValue()));
                case GREATER_THAN ->
                        criteriaBuilder.gt(attribute.as(Number.class), (Number) castToRequiredType(attribute.getJavaType(), input.getValue()));
                case LESS_THAN ->
                        criteriaBuilder.lt(attribute.as(Number.class), (Number) castToRequiredType(attribute.getJavaType(), input.getValue()));
                case LIKE -> criteriaBuilder.like(attribute.as(String.class), "%" + input.getValue() + "%");
                case IN -> {
                    List<Object> castedValues = new ArrayList<>();
                    for (String value : input.getValues()) {
                        castedValues.add(castToRequiredType(attribute.getJavaType(), value));
                    }
                    yield attribute.in(castedValues);
                }
                default -> throw new RuntimeException("Operation not supported yet");
            };
        };
    }

    private Object castToRequiredType(Class fieldType, String value) {

        if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

    private <T> Path<T> getAttribute(From<?, Employee> root, String field) {
        String[] parts = field.split("\\.");
        From<?, Employee> join = root;
        for (int i = 0; i < parts.length - 1; i++) {
            join = join.join(parts[i]);
        }
        Join<Employee, ?> attribute = (Join<Employee, T>) join;
        return attribute.get(parts[parts.length - 1]);
    }
}
