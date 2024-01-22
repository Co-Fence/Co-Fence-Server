package com.gdsc.cofence.service.report;

import com.gdsc.cofence.repository.ReportRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class ReportUpdateService {

    private final ReportRepository reportRepository;


}
