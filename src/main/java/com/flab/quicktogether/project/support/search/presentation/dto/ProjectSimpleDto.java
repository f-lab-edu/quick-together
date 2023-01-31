package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectSimpleDto {
    private Project project;
    private Long likes;

}
