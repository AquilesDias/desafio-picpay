package com.picpaysimplificado.domain.user;

import com.picpaysimplificado.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String document;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User (UserDTO userDTO){
        this.firstName = userDTO.firstName();
        this.lastName  = userDTO.lastName();
        this.password  = userDTO.password();
        this.email     = userDTO.email();
        this.document  = userDTO.document();
        this.balance   = userDTO.balance();
        this.userType  = userDTO.userType();
    }
}
