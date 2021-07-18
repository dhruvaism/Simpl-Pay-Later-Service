package com.simpl.paylater.enitity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "credit_limit", nullable = false)
    private int credit_limit;

    @Column(name = "dues", nullable = false)
    private int dues;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_name", referencedColumnName = "name")
    private List<Transaction> transactions = new ArrayList<>();




}
