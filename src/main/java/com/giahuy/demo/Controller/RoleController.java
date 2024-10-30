package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.PermissionService;
import com.giahuy.demo.Service.RoleService;
import com.giahuy.demo.Service.UserService;
import com.giahuy.demo.dto.request.ApiResponse;
import com.giahuy.demo.dto.request.PermissionRequest;
import com.giahuy.demo.dto.request.RoleRequest;
import com.giahuy.demo.dto.response.PermissionResponse;
import com.giahuy.demo.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder().build();
    }

}
