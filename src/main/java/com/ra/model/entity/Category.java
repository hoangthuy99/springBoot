package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String categoryName;
        private Boolean status = true;
        @OneToMany(mappedBy = "category")
        private List<Product> products;

}
