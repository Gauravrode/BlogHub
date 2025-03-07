package com.acts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="role")
@NoArgsConstructor
@Getter
@Setter
public class Role {
	
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id;
   
   private String name;
}
