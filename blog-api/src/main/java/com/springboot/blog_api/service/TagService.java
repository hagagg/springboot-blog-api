package com.springboot.blog_api.service;

import com.springboot.blog_api.dao.TagDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.tag.TagRequestDto;
import com.springboot.blog_api.dto.tag.TagResponseDto;
import com.springboot.blog_api.entity.Tag;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;
    private final UserDao userDao;
    private final TagDao tagDao;

    public ResponseEntity<TagResponseDto> create(TagRequestDto tagRequestDto, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Tag tag = tagMapper.toEntity(tagRequestDto);
        tagDao.save(tag);

        return new ResponseEntity<>(tagMapper.toDto(tag), HttpStatus.CREATED);
    }

    public Tag findOrCreateByName(String name) {

      String cleanedName = name.trim().toLowerCase();

      return tagDao.findByName(cleanedName)
              .orElseGet(()-> tagDao.save(Tag.builder()
                      .name(cleanedName).build()));
    }
}
