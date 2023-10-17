package com.hieuph.todosmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Date dateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "todo_id", referencedColumnName = "id")
    private Todo todo;



}
