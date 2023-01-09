package com.flab.quicktogether.project.application.dto;

import com.flab.quicktogether.globalsetting.domain.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditRecruitmentPositionsRequestDto {
    private Position recruitmentPosition;
}
