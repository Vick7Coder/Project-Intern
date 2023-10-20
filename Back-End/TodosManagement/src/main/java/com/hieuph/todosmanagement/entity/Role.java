package com.hieuph.todosmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "role")
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}