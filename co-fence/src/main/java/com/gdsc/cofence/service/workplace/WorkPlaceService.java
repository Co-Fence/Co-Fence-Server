package com.gdsc.cofence.service.workplace;

import com.gdsc.cofence.dto.wokrplaceDto.WorkPlaceMetaDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlacePagingAmountRequestDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlacePagingRequestDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseWrapperDto;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.WorkplaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@AllArgsConstructor
@Transactional
public class WorkPlaceService {

    private final WorkplaceRepository workplaceRepository;

    public WorkPlaceResponseWrapperDto getWorkPlaces(WorkPlacePagingAmountRequestDto requestDto,
                                                     Principal principal) {

        if (principal == null) { // principal 객체에 null담기는거 문제 해결하기
            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION,
                    ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
        }

        Pageable pageable = PageRequest.of(requestDto.getPage() - 1, requestDto.getSize());

        Page<WorkPlaceResponseDto> result = workplaceRepository.findAll(pageable)
                .map(WorkPlaceResponseDto::new);

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

//    @Transactional(readOnly = true)
//    public Page<WorkPlacePagingRequestDto> searchWorkPlace(String keyword, WorkPlacePagingAmountRequestDto pagingRequestDto, Principal principal) {
//
//        if (principal == null) {
//            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION,
//                    ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
//        }
//
//        PageRequest pageRequest = PageRequest.of(pagingRequestDto.getPage(), pagingRequestDto.getSize());
//
//
//
//        return
//    }
}
