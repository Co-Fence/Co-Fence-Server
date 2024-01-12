package com.gdsc.cofence.repository;

import com.gdsc.cofence.entity.workplace.WorkPlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;

public interface WorkplaceRepository extends JpaRepository<WorkPlace, Long>{

    Page<WorkPlace> findByWorkplaceNameContaining(String workplaceName, Pageable pageable);
}
