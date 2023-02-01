package com.flab.quicktogether.project.support.post.presentation;

import com.flab.quicktogether.project.support.post.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    @NotNull
    private Long memberId;

    @NotEmpty
    private String content;

    @NotNull
    private LocalDateTime createDateTime;

    private PostResponse(Long memberId, String content, LocalDateTime createDateTime) {
        this.memberId = memberId;
        this.content = content;
        this.createDateTime = createDateTime;
    }

    public static PostResponse toDto(Post post) {
        PostResponse postResponse = new PostResponse(post.getMember().getId(), post.getContent(), post.getCreateDateTime());
        return postResponse;
    }
}
