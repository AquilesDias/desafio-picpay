package com.picpaysimplificado.dto;

import com.picpaysimplificado.domain.user.User;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {
}
