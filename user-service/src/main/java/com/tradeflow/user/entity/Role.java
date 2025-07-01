package com.tradeflow.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN
}