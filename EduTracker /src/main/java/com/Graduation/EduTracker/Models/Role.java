package com.Graduation.EduTracker.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
public class Role {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private long id;
  @Enumerated(EnumType.STRING)
  private RoleEnum RoleName;
  private String Description;
}
