package com.trainingup.trainingupapp.tables;

import com.trainingup.trainingupapp.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name="EMAIL")
@EnableAutoConfiguration
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String trainUpEmail;
    private UserType userType;
}
