package com.flab.quicktogether.project.presentation.dto.request;

import com.flab.quicktogether.project.domain.Position;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProjectRecruitmentPositionsDto {
    @NotNull
    private Position recruitmentPosition;

}
