package com.marcus.profile.mapper;

import org.mapstruct.Mapper;

import com.marcus.profile.dto.request.ProfileCreationRequest;
import com.marcus.profile.dto.response.UserProfileResponse;
import com.marcus.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
