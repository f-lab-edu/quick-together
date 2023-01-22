package com.flab.quicktogether.project.support.post.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.post.exception.ContentLengthException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    private final static int contentMaxSize = 300;
    private final static int contentEmpty = 0;
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    public Post(Project project, Member member, String content) {
        this.project = project;
        this.member = member;
        this.content = content;
    }

    public static Post createPost(Project project, Member member, String content) {
        validateContent(content);
        return new Post(project, member, content);
    }

    public void changeContent(String changedContent){
        validateContent(changedContent);
        this.content = changedContent;
    }

    private static void validateContent(String content){
        if (contentMaxSize < content.length() || content.length() == contentEmpty) {
            throw new ContentLengthException();
        }
    }
}
