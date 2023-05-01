package com.web.clothes.ClothesWeb.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    @Column(nullable = false, length = 60)
    private String fullName;
    
    @Column(nullable = false, unique = true,length = 60)
    private String userName;
    
    @Column(nullable = false,length = 60)
    private String password;
    
    @Email
    @Column(nullable = false,unique = true,length = 60)
    private String email;
    
    @Column(nullable = false,unique =true, length = 11)
    private String phone;
    
    @Column(nullable = true,length = 100)
    private String address;
    
    @Column(nullable = false)
    private boolean active;
    
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createAt = LocalDate.now();
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate deleteAt;
    
    @Column(nullable = false)
    private int deleted = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Role role;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Feedback> feedback = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();
}
