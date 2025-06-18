package com.springboot.blog_api.service.impl;

import com.springboot.blog_api.dao.TagDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.tag.TagRequestDto;
import com.springboot.blog_api.dto.tag.TagResponseDto;
import com.springboot.blog_api.entity.Tag;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.mapper.TagMapper;
import com.springboot.blog_api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final UserDao userDao;
    private final TagDao tagDao;

    public TagResponseDto create(TagRequestDto tagRequestDto, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Tag tag = tagMapper.toEntity(tagRequestDto);
        tagDao.save(tag);

        return tagMapper.toDto(tag);
    }

    public Tag findOrCreateByName(String name) {

      String cleanedName = name.trim().toLowerCase();

      return tagDao.findByName(cleanedName)
              .orElseGet(()-> tagDao.save(Tag.builder()
                      .name(cleanedName).build()));
    }

}
