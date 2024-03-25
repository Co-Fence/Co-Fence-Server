package com.gdsc.cofence.entity.report;

import com.gdsc.cofence.global.exception.ErrorCode;
import com.gdsc.cofence.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum ActionStatus {

    BEFORE_ACTION("BEFORE_ACTION", "조치전"),
    IN_ACTION("IN_ACTION", "조치중"),
    WORK_SUSPEND("WORK_SUSPEND", "중단된 작업"),
    ACTION_COMPLETED("ACTION_COMPLETED", "조치완료");

    private final String code;
    private final String displayName;

    public static ActionStatus fromDisplayName(String displayName) {
        for (ActionStatus actionStatus : ActionStatus.values()) {
            if (actionStatus.getDisplayName().equals(displayName)) {
                return actionStatus;
            }
        }

        throw new CustomException(ErrorCode.INVALID_DISPLAY_NAME_EXCEPTION,
                ErrorCode.INVALID_DISPLAY_NAME_EXCEPTION.getMessage());
    }
}
