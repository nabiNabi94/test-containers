package com.example.testcontainersexample.entity;

import com.example.testcontainersexample.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.messaging.handler.annotation.Payload;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_first_name")
    private String firstName;
    @Column(name = "user_last_name")
    private String lastName;

    public User(UserDTO userDTO){
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
    }

}
