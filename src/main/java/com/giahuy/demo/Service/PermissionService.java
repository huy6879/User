package com.giahuy.demo.Service;

import com.giahuy.demo.dto.request.PermissionRequest;
import com.giahuy.demo.dto.response.PermissionResponse;
import com.giahuy.demo.entity.Permission;
import com.giahuy.demo.mapper.PermissionMapper;
import com.giahuy.demo.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    };

    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();

        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionId) {
        permissionRepository.deleteById(permissionId);
    }

}
