package com.betacom.disco.specifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.betacom.disco.dtos.EventFilter;
import com.betacom.disco.entities.Event;

import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EventSpecification {

    public static Specification<Event> fromFilter(EventFilter filter) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // ----- available ------
            // If "available" exists
            if (filter.getAvailable() != null) {
                // If "available" = true
                if (filter.getAvailable()) {
                    Predicate isNotPast = criteriaBuilder
                            .greaterThanOrEqualTo(root.get("date"), LocalDate.now());

                    predicates.add(criteriaBuilder.and(isNotPast));
                } // If "available" = false
                else {
                    Predicate isPast = criteriaBuilder
                            .lessThan(root.get("date"), LocalDate.now());

                    predicates.add(criteriaBuilder.and(isPast));
                    
                }
            }
            // ----- maxPrice ------
            if (filter.getMaxPrice() != null && filter.getMaxPrice() > 0) {
                Predicate isGreaterThanMaxPrice = criteriaBuilder
                        .lessThanOrEqualTo(root.get("basePrice"), filter.getMaxPrice());

                predicates.add(criteriaBuilder.and(isGreaterThanMaxPrice));
            }



            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
