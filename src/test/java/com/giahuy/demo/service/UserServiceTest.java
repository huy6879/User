package com.giahuy.demo.service;

import com.giahuy.demo.Service.UserService;
import com.giahuy.demo.dto.request.UserCreationRequest;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.User;
import com.giahuy.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;

    private UserResponse response;

    private User user;

    private LocalDate dob;



    @BeforeEach
    void initData(){
        dob = LocalDate.of(1990,1,12);
        request = UserCreationRequest.builder()
                .username("John")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("16fda7c85132")
                .username("John")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("16fda7c85132")
                .username("John")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // Giả lập: Khi gọi existsByUsername với bất kỳ chuỗi nào, trả về false
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(false);

        // Giả lập: Khi gọi save với bất kỳ đối tượng nào, trả về user
        Mockito.when(userRepository.save(any()))
                .thenReturn(user);

        // Khi: Gọi createUser với request
        var response = userService.createUser(request);

        // Thì: Kiểm tra kết quả
        Assertions.assertThat(response.getId()).isEqualTo("16fda7c85132");
        Assertions.assertThat(response.getUsername()).isEqualTo("John");
    }

}
