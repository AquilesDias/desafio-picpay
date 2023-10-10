package com.picpaysimplificado.dto;

import com.picpaysimplificado.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String firstName,
        String lastName,
        String password,
        String email,
        String document,
        BigDecimal balance,
        UserType userType) {
}
