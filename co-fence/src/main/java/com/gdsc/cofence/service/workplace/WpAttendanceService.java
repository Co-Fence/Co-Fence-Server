package com.gdsc.cofence.service.workplace;

import com.gdsc.cofence.repository.WorkplaceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class WpAttendanceService {

    private final WorkplaceRepository workplaceRepository;



}
