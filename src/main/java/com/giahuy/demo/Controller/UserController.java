package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.UserService;
import com.giahuy.demo.dto.request.ApiResponse;
import com.giahuy.demo.dto.request.UserCreationRequest;
import com.giahuy.demo.dto.request.UserUpdateRequest;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.User;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping()
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller: create User");
        ApiResponse<UserResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }


    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
//        var authentication =  SecurityContextHolder.getContext().getAuthentication();
//
//        log.info("Username : {}", authentication.getName());
//
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }


    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("{userId}")
    String deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return  "User has been deleted";
    }
}
