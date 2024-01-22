package com.gdsc.cofence.repository;

import com.gdsc.cofence.entity.report.ReportManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportManagement, Long>,
        JpaSpecificationExecutor<ReportManagement> {

}
