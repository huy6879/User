package com.giahuy.demo.Service;

import com.giahuy.demo.constant.PredefinedRole;
import com.giahuy.demo.dto.request.UserCreationRequest;
import com.giahuy.demo.dto.request.UserUpdateRequest;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.Role;
import com.giahuy.demo.entity.User;
import com.giahuy.demo.exception.AppException;
import com.giahuy.demo.exception.ErrorCode;
import com.giahuy.demo.mapper.UserMapper;
import com.giahuy.demo.repository.RoleRepository;
import com.giahuy.demo.repository.UserRepository;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    AuthenticationService authenticationService;

    public UserResponse createUser(UserCreationRequest request){

        log.info("Service: createUser");

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User u = userMapper.toUser(request);

        u.setPassword(passwordEncoder.encode(u.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        u.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(u));
    }

    @PostAuthorize("hasRole('ADMIN') or (returnObject.username == authentication.name)")
    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        if(request.getPassword().length() >= 5)
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        else throw new AppException(ErrorCode.PASSWORD_INVALID); // Thêm mã lỗi tùy ý

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById((userId));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();

        String name = context.getAuthentication().getName();
        log.info(name);

        User user = userRepository.findByUsername(name)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User Not Found")));
    }


}
