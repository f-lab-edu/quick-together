package com.flab.quicktogether.project.presentation.dto.request;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.project.application.dto.EditRecruitmentPositionsRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditRecruitmentPositionsRequest {
    @NotNull
    private Position recruitmentPosition;

    public EditRecruitmentPositionsRequestDto toServiceDto() {
        return new EditRecruitmentPositionsRequestDto(this.getRecruitmentPosition());
    }
}
