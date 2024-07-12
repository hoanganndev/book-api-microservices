package com.marcus.identity.mapper;

import org.mapstruct.Mapper;

import com.marcus.identity.dto.request.ProfileCreationRequest;
import com.marcus.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
