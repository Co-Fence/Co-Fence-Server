package com.gdsc.cofence.entity.report;

import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum ReportStatus {

    FIRE_HAZARD("FIRE_HAZARD", "화재 위험"),
    COLLAPSE_HAZARD("COLLAPSE_HAZARD", "붕괴 위험"),
    MECHANICAL_FAILURE("MECHANICAL_FAILURE", "기계 결함"),
    CHEMICAL_SPILL_HAZARD("CHEMICAL_SPILL_HAZARD", "화학 물질 누출 위험"),
    ELECTRICAL_HAZARD("ELECTRICAL_HAZARD", "전기 위험"),
    FALLING_HAZARD("FALLING_HAZARD", "탈락 위험"),
    EXPLOSION_HAZARD("EXPLOSION_HAZARD", "폭발 위험"),
    ETC_HAZARD("ETC_HAZARD", "기타 위험");

    private final String code;
    private final String displayName;

    public static ReportStatus fromDisplayName(String displayName) {
        for (ReportStatus reportStatus : ReportStatus.values()) {
            if (reportStatus.getDisplayName().equals(displayName)) {
                return reportStatus;
            }
        }

        throw new CustomException(ErrorCode.INVALID_DISPLAY_NAME_EXCEPTION,
                ErrorCode.INVALID_DISPLAY_NAME_EXCEPTION.getMessage());
    }
}
