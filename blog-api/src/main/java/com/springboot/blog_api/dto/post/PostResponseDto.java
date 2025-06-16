package com.springboot.blog_api.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long id;
    private String content;
    private LocalDateTime publishDate;
    private LocalDateTime updateDate;
    private String creatorName;
    private int likesCount;
    private List<String> comments;
    private List<String> tags;
    private List<String> categories;


}
