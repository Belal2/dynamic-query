package com.dynamic.querydsl.repositories;

import com.dynamic.entity.Employee;
import com.dynamic.entity.QEmployee;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryByQueryDsl extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee>, QuerydslBinderCustomizer<QEmployee> {
    Employee findManagerByEmployeeId(Long employeeId);

    @Override
    default void customize(QuerydslBindings bindings, QEmployee root) {
        bindings.bind(String.class).first(new SingleValueBinding<StringPath, String>() {
            @Override
            public Predicate bind(StringPath path, String value) {
                return path.containsIgnoreCase(value);
            }
        });
    }

}
