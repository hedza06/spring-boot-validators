package com.starter.springboot.rest.dto;

import org.springframework.context.annotation.Description;

@Description(value = "Example DTO object.")
public class ExampleDTO {

    private String username;
    private Integer age;
    private Boolean employed;
    private String profession;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getEmployed() {
        return employed;
    }

    public void setEmployed(Boolean employed) {
        this.employed = employed;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
