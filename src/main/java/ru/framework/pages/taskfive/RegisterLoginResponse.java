package ru.framework.pages.taskfive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
public class RegisterLoginResponse {
    private int id;
    private String token;
    private String error;
}