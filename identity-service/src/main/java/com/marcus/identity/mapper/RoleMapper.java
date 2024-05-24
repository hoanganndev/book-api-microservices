package com.marcus.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcus.identity.dto.request.RoleRequest;
import com.marcus.identity.dto.response.RoleResponse;
import com.marcus.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
