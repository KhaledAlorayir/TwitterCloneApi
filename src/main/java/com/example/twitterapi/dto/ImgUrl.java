package com.example.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgUrl {
    @NotBlank(message = "link is required")
    @URL
    private String img_url;

    public void setImg_url(String img_url) {
        this.img_url = img_url.trim();
    }
}
