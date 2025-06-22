package com.springboot.blog_api.dto.tag;

import com.springboot.blog_api.dto.post.PostSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagResponseDto {

    private Long id;
    private String name;
    private List<PostSummaryDto> posts;

}
