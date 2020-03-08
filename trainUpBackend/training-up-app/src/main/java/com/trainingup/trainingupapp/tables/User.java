package com.trainingup.trainingupapp.tables;

import com.trainingup.trainingupapp.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@EnableAutoConfiguration
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private UserType type;
    private String firstName;
    private String lastName;
    private String password;
    private String token;
    private boolean enable;
    private LocalDate dateOfRegistration;
    private String leader;
    private int accepted;
    private int rejected;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Course> courses = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Course> wishToEnroll = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Course> waitToEnroll = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Course> rejectedList = new ArrayList<>();

}