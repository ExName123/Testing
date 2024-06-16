package ru.framework.pages.taskfive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class UsersPage {
    private Integer page;
    Integer per_page;
    Integer total;
    Integer total_pages;
    List<UserData> data;

    Support support;

}
