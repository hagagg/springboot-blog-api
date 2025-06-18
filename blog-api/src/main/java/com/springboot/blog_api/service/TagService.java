package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.tag.TagRequestDto;
import com.springboot.blog_api.dto.tag.TagResponseDto;
import com.springboot.blog_api.entity.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TagService {

    TagResponseDto create(TagRequestDto tagRequestDto, Authentication auth);

    Tag findOrCreateByName(String name);
}
