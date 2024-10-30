package com.giahuy.demo.dto.request;

import com.giahuy.demo.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     @Size(min = 3, message = "USERNAME_INVALID")
     String username;
     @Size(min = 5, message = "PASSWORD_INVALID")
     String password;
     String firstName;
     String lastName;
     @DobConstraint(min = 2, message = "INVALID_DOB")
     LocalDate dob;
     List<String> roles;
}
