package com.personalfinancetracker.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "category name is required")
    @Column(nullable = false)
    private String categoryName; // name,type,userid

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    public enum CategoryType {INCOME,EXPENSE}

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
