package com.giahuy.demo.mapper;

import com.giahuy.demo.dto.request.RoleRequest;
import com.giahuy.demo.dto.response.RoleResponse;
import com.giahuy.demo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
