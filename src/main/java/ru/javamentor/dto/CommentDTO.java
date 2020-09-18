package ru.javamentor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO {
 private String text;
 private Long TopicId;
 private Boolean isMainComment;
 private Long mainCommentId;
}
