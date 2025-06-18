package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.tag.TagRequestDto;
import com.springboot.blog_api.dto.tag.TagResponseDto;
import com.springboot.blog_api.entity.Tag;
import com.springboot.blog_api.mapper.TagMapper;
import com.springboot.blog_api.service.TagService;
import com.springboot.blog_api.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("tag")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @PostMapping("create")
    public ResponseEntity<TagResponseDto> create (@RequestBody TagRequestDto tagRequestDto , Authentication auth) {
        return new ResponseEntity<>( tagService.create (tagRequestDto , auth) , HttpStatus.CREATED);
    }

    @PostMapping("find-or-create")
    public ResponseEntity<TagResponseDto> findOrCreateCategory (@RequestParam String name) {
        Tag tag = tagService.findOrCreateByName(name );
        return new ResponseEntity<> (tagMapper.toDto(tag), HttpStatus.OK);

    }

}
