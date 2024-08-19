package com.marcus.postservice.controller;

import com.marcus.postservice.dto.ApiResponse;
import com.marcus.postservice.dto.request.PostRequest;
import com.marcus.postservice.dto.response.PageResponse;
import com.marcus.postservice.dto.response.PostResponse;
import com.marcus.postservice.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping("/create")
    ApiResponse<PostResponse> createPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }


    @PostMapping("/create-path")
    ApiResponse<List<PostResponse>> createPosts(@RequestParam int number) {
        return ApiResponse.<List<PostResponse>>builder()
                .result(postService.createPosts(number))
                .build();
    }

    @GetMapping("/my-post")
    ApiResponse<PageResponse<PostResponse>> getListPost(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size

    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .result(postService.getPosts(page,size))
                .build();
    }
}