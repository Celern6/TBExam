package com.tothandras.talkabotexam.todo.services;

import com.tothandras.talkabotexam.todo.exceptions.InvalidPriorityException;
import com.tothandras.talkabotexam.todo.model.Todo;
import com.tothandras.talkabotexam.todo.model.request.DateFilterCriteria;
import com.tothandras.talkabotexam.todo.model.request.FilterCriteria;
import com.tothandras.talkabotexam.todo.model.request.SortingCriteria;
import com.tothandras.talkabotexam.todo.model.request.TodoListRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class SortingServiceImpl implements SortingService{

    private final static String ASCENDING_ORDER = "asc" ;

    private final static String DESCENDING_ORDER = "desc" ;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Todo> processRequest(TodoListRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Todo> cr = cb.createQuery(Todo.class);
        Root<Todo> root = cr.from(Todo.class);
        List<Predicate> predicates = new LinkedList<>();
        List<Order> orderList = new LinkedList<>();

        // Filter
        if (request.getFilters() != null && !request.getFilters().isEmpty()) {
            List<FilterCriteria> filters = request.getFilters();
            for (FilterCriteria f : filters) {
                String value = f.getFieldName().equalsIgnoreCase("priority") ? getPriorityDbValue(f.getValue()) : f.getValue();
                predicates.add(cb.equal(root.get(f.getFieldName()), value));
            }
        }

        // DateFilter
        if (request.getDateFilters() != null && !request.getDateFilters().isEmpty()) {
            List<DateFilterCriteria> datefilters = request.getDateFilters();
            datefilters.forEach(f -> {
                if (f.getStartDate() != null && f.getEndDate() != null) {
                    predicates.add(cb.between(root.get(f.getFieldName()), f.getStartDate(), f.getEndDate()));
                } else if (f.getStartDate() != null && f.getEndDate() == null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get(f.getFieldName()), f.getStartDate()));
                } else if (f.getStartDate() == null && f.getEndDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get(f.getFieldName()), f.getEndDate()));
                }
            });
        }

        // Finishing
        cr.select(root).where(predicates.toArray(new Predicate[0]));

        // Sorting
        if (request.getSorting() != null && !request.getSorting().isEmpty()) {
            List<SortingCriteria> sortingCriteria = request.getSorting();
            sortingCriteria.forEach(s -> {
                String fieldName = s.getFieldName();
                if (s.getDirection().equals(ASCENDING_ORDER)) {
                    orderList.add(cb.asc(root.get(fieldName)));
                } else if (s.getDirection().equals(DESCENDING_ORDER)) {
                    orderList.add(cb.desc(root.get(fieldName)));
                }
            });
            cr.orderBy(orderList);
        }

        // Processing offset & limit
        if(request.getOffset() != null && request.getLimit() != null) {
            return entityManager.createQuery(cr)
                    .setFirstResult(request.getOffset())
                    .setMaxResults(request.getLimit()).getResultList();
        }

        return entityManager.createQuery(cr).getResultList();
    }

    private String getPriorityDbValue(String input) {
        return switch(input) {
            case "HIGH" -> "2";
            case "MEDIUM" -> "1";
            case "LOW" -> "0";
            default -> throw new InvalidPriorityException();
        };
    }
}
