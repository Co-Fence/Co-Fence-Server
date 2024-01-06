package com.gdsc.cofence.controller.workplace;

import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseDto;
import com.gdsc.cofence.service.workplace.WorkPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v22/wp")
public class WorkplaceController {

    private final WorkPlaceService workPlaceService;

    @GetMapping("/getInfo")
    public Page<WorkPlaceResponseDto> getWorkplaces(@RequestParam int page, @RequestParam int size) {
        return workPlaceService.getWorkPlaces(page, size);
    }
}
