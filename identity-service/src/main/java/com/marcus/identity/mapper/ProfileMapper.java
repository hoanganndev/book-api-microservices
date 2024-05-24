package com.marcus.identity.mapper;

import com.marcus.identity.dto.request.ProfileCreationRequest;
import com.marcus.identity.dto.request.UserCreationRequest;
import com.marcus.identity.dto.request.UserUpdateRequest;
import com.marcus.identity.dto.response.UserResponse;
import com.marcus.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
