package com.example.postProject.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Comment {
    @Id
    private Long id;
    private Long boardId;
    private String author;
    private String content;
    private String password;
    private LocalDateTime createdAt;

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Comment() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
