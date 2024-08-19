package com.marcus.postservice.mapper;

import com.marcus.postservice.dto.response.PostResponse;
import com.marcus.postservice.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
