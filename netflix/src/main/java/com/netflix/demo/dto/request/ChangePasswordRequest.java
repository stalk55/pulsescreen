package com.netflix.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message="Current password id required")
    private String currentpassword;

    @NotBlank(message = "Newpassword is required")
    private String newPassword;

}
