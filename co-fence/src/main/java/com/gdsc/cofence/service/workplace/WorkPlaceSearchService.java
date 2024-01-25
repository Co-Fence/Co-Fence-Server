package com.gdsc.cofence.service.workplace;

import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceMetaDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlacePagingAmountRequestDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseWrapperDto;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.WorkplaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class WorkPlaceSearchService {

    private final WorkplaceRepository workplaceRepository;

    // 전체 작업현장을 페이지네이션을 통해서 불러옴
    public WorkPlaceResponseWrapperDto getWorkPlaces(WorkPlacePagingAmountRequestDto requestDto,
                                                     Principal principal) {

        if (principal == null) { // principal 객체에 null담기는거 문제 해결
            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION,
                    ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
        }

        Pageable pageable = PageRequest.of(requestDto.getPage() - 1, requestDto.getSize());

        Page<WorkPlaceResponseDto> result = workplaceRepository.findAll(pageable)
                .map(WorkPlaceResponseDto::new);

        // 해당 검색결과에 대한 작업현장 내역이 없는 경우
        if (result.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_REPORTS_EXCEPTION,
                    ErrorCode.NOT_FOUND_REPORTS_EXCEPTION.getMessage());
        }

        boolean hasMore = hasMore(requestDto.getPage(), requestDto.getSize());
        WorkPlaceMetaDto meta = WorkPlaceMetaDto.builder()
                .count(result.getContent().size())
                .page(requestDto.getPage())
                .hasMore(hasMore)
                .build();

        return WorkPlaceResponseWrapperDto.builder()
                .meta(meta)
                .data(result.getContent())
                .build();
    }

    // 더 표현할 데이터가 있는지에 대한 검증
    // true면 더 조회할 데이터가 있다는 것 / false면 더 조회할 데이터가 없다는것이다
    // findAll()을 사용안하는 방법으로 한 번 찾아봤음
    private Boolean hasMore(int page, int size) {
        long totalElements = workplaceRepository.count();

        return totalElements > (long) page * size;
    }

    @Transactional(readOnly = true)
    public Page<WorkPlaceResponseDto> searchWorkPlaceByName(String workplaceName, Pageable pageable , Principal principal) {

        if (principal == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION,
                    ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
        }

        return workplaceRepository.findByWorkplaceNameContaining(workplaceName, pageable)
                .map(WorkPlaceResponseDto::new);
    }

    public WorkPlaceResponseDto searchWorkPlaceById(Long workPlaceId, Principal principal) {

        if (principal == null) { // principal 객체에 null담기는거 문제 해결하기
            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION,
                    ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
        }

        WorkPlace workPlaceInfo = workplaceRepository.findById(workPlaceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        return WorkPlaceResponseDto.builder()
                .workPlaceId(workPlaceInfo.getWorkplaceId())
                .workPlaceName(workPlaceInfo.getWorkplaceName())
                .workPlaceAddress(workPlaceInfo.getWorkplaceAddress())
                .build();
    }
}