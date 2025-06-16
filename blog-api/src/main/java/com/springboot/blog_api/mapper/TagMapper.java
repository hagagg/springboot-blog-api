package com.springboot.blog_api.mapper;

import com.springboot.blog_api.dto.tag.TagRequestDto;
import com.springboot.blog_api.dto.tag.TagResponseDto;
import com.springboot.blog_api.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagMapper {

    private final PostMapper postMapper;

    public TagResponseDto toDto(Tag tag) {

        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .posts(tag.getPosts().stream().map(postMapper::toSummaryDto).collect(Collectors.toList()) )
                .build();
    }

    public Tag toEntity (TagRequestDto tagRequestDto) {

        return Tag.builder()
                .name(tagRequestDto.getName())
                .build();
    }
}
