package com.example.twitterapi.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class BioDTO {
    @Length(max = 300)
    private String bio;
}
