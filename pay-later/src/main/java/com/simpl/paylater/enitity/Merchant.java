package com.simpl.paylater.enitity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "merchant")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Merchant {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false)
    private double discount_percentage;

    @Column(name = "discount", nullable = false)
    private double discount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "merchant_name", referencedColumnName = "name")
    private List<Transaction> transactions = new ArrayList<>();

}
