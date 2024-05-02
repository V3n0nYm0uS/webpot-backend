package com.webpot.webpotbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="category")
@Table(name = "category", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"label"}),
    @UniqueConstraint(columnNames = {"color"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column()
    private String color;
}
