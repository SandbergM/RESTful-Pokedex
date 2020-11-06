package com.example.Pokedex.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

public class User {
    @Id
    @ApiModelProperty( notes = "The MongoDb _id" )
    private String id;
    @ApiModelProperty( notes = "The users first name" )
    private String firstName;
    @ApiModelProperty( notes = "The users last name" )
    private String lastName;
    @NotEmpty( message = "Users is required to have a username")
    @ApiModelProperty( notes = "The users unique username" )
    private String username;
    @NotEmpty( message = "Users is required to have email")
    @ApiModelProperty( notes = "The unique email belonging to the user" )
    private String email;
    @NotEmpty( message = "Users is required to have a password")
    @Size(min = 8, max = 12)
    @ApiModelProperty( notes = "The users password, min 8 - max 12" )
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty( notes = "The users date of birth, format yyyy-MM-dd" )
    private LocalDate dateOfBirth;
    @ApiModelProperty( notes = "Contains the roles a user has in the system" )
    private List<String> roles;

    public User(
            String firstName,
            String lastName,
            String email,
            String password,
            LocalDate dateOfBirth,
            List<String> roles
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.roles = roles;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
