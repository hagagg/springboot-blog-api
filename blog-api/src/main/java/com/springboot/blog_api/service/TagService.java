package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.tag.TagRequestDto;
import com.springboot.blog_api.dto.tag.TagResponseDto;
import com.springboot.blog_api.entity.Tag;

public interface TagService {

    TagResponseDto create(TagRequestDto tagRequestDto);

    Tag findOrCreateByName(String name);
}
