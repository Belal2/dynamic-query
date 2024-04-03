package com.dynamic.specification.service;

import com.dynamic.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeByCriteria {

    private final EntityManager entityManager;

    public List<Employee> findEmployeesByCriteria(String field, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        Predicate predicate = buildPredicate(root, field, value, criteriaBuilder);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
    Predicate buildPredicate(Root<Employee> root, String field, Object value, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (field != null && value != null) {
            String[] fieldParts = field.split("\\.");
            From<Employee, ?> join = root;
            for (int i = 0; i < fieldParts.length - 1; i++) {
                join = join.join(fieldParts[i]);
            }
            Join<Employee, ?> attribute = (Join<Employee, ?>) join;
            // hena ya malam mmkn ndef another operators baka
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(attribute.get(fieldParts[fieldParts.length - 1]), value));
        }
        return predicate;
    }
}
