package com.gdsc.cofence.service.workplace;

import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseDto;
import com.gdsc.cofence.repository.WorkplaceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class WorkPlaceService {

    private final WorkplaceRepository workplaceRepository;

    public Page<WorkPlaceResponseDto> getWorkPlaces(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<WorkPlaceResponseDto> map = workplaceRepository.findAll(pageable)
                .map(WorkPlaceResponseDto::new);

        return map;
    }

}
