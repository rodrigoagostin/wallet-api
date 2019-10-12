package com.project.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class UserDTO {

    private Long id;

    @Length(min = 3, max = 50, message = "Name should be characters between 3 and 50")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @Length(min = 6, message = "Password should be bigger than 6 characters")
    private String password;
}
