package com.marcus.postservice.service;

import com.marcus.postservice.dto.request.PostRequest;
import com.marcus.postservice.dto.response.PageResponse;
import com.marcus.postservice.dto.response.PostResponse;
import com.marcus.postservice.entity.Post;
import com.marcus.postservice.mapper.PostMapper;
import com.marcus.postservice.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.marcus.postservice.utils.StringUtils.generateRandomText;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    DateTimeFormatter dateTimeFormatter;
    PostRepository repository;
    PostMapper postMapper;

    public PostResponse createPost(PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = Post.builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();
        post = repository.save(post);
        return postMapper.toPostResponse(post);
    }


    public List<PostResponse> createPosts(int numberRecord) {
        if (numberRecord <= 0) return List.of();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PostResponse> posts = new ArrayList<>();
        for (int i = 0; i < numberRecord; i++) {
            String content = generateRandomText(10);
            Post post = Post.builder()
                    .content(content)
                    .userId(authentication.getName())
                    .createdDate(Instant.now())
                    .modifiedDate(Instant.now())
                    .build();
            posts.add(postMapper.toPostResponse(repository.save(post)));
        }
        return posts;
    }


    public List<PostResponse> getAllPost() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return repository.findAllByUserId(userId)
                .stream()
                .map(postMapper::toPostResponse)
                .toList();
    }

    public PageResponse<PostResponse> getPosts(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Post> pageData = repository.findAllByUserId(userId, pageable);
        List<PostResponse> postList = pageData.getContent().stream().map(post -> {
            PostResponse postResponse = postMapper.toPostResponse(post);
            postResponse.setCreated(dateTimeFormatter.format(post.getCreatedDate()));
            return postResponse;
        }).toList();
        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }
}
