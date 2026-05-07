package com.netflix.demo.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VideoRequest {

    @NotBlank(message = "titel required")
    private String titel;

    @Size(max=4000 , message = "should not be more than 4000 characters")
    private String description;

    private Integer year;
    private String rating;
    private Integer duration;
    private String src;
    private String poster;
    private boolean published;
    private List<String> categories;
}
