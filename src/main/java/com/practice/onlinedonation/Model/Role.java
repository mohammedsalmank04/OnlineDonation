package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppRole roleName;


}
