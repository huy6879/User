package com.giahuy.demo.mapper;


import com.giahuy.demo.dto.request.PermissionRequest;
import com.giahuy.demo.dto.request.UserCreationRequest;
import com.giahuy.demo.dto.request.UserUpdateRequest;
import com.giahuy.demo.dto.response.PermissionResponse;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.Permission;
import com.giahuy.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
