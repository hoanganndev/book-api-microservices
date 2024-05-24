package com.marcus.identity.mapper;

import org.mapstruct.Mapper;

import com.marcus.identity.dto.request.PermissionRequest;
import com.marcus.identity.dto.response.PermissionResponse;
import com.marcus.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
