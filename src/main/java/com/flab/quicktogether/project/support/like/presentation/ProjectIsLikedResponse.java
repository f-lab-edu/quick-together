package com.flab.quicktogether.project.support.like.presentation;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectIsLikedResponse {
    @NotNull
    private Long projectId;

    @NotNull
    private boolean liked;

}
