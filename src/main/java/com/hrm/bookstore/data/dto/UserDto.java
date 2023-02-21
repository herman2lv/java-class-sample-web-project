package com.hrm.bookstore.data.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Role role;

    public enum Role {
        USER, MANAGER, ADMIN
    }

}
