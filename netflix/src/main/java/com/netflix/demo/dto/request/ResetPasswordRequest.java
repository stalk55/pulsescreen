package com.netflix.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank
    private String token;

    @NotBlank(message = "new password is required")
    @Size(min = 6 , message ="password must have atleast 6 carachters")
    private String newPassword;

}
