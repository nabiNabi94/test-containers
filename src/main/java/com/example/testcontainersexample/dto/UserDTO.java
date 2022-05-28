package com.example.testcontainersexample.dto;

import com.fasterxml.jackson.core.SerializableString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String firstName;
    private String lastName;

}
