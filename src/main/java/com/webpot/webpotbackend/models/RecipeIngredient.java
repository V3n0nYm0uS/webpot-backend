package com.webpot.webpotbackend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable=false)
    private Ingredient ingredient;

    @Column(nullable = false)
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
