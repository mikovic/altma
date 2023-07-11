package com.mikovic.altma.payload;

import com.mikovic.altma.validation.FieldMatch;
import com.mikovic.altma.validation.PasswordMatches;
import com.mikovic.altma.validation.ValidEmail;
import com.mikovic.altma.validation.ValidWord;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "{password.match}")
public class SignUpRequest {

    private Long id;

    @Size(
            min = 2,
            max = 30,
            message = "{validatedSize}"
    )
    @ValidWord
    private String firstName;

    @NotBlank
    @Size(
            min = 2,
            max = 30,
            message = "{validatedSize}"
    )
    @ValidWord
    private String lastName;

    @NotBlank
    @Size(
            min = 2,
            max = 30,
            message = "{validatedSize}"
    )
    @ValidWord(message = "{valid.word}")
    private String nickName;

    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    @Size(min = 1,
            max = 20,
            message = "{is.required}")
    private String password;

    @NotBlank
    @Size(min = 1,
            max = 20)
    private String rePassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
