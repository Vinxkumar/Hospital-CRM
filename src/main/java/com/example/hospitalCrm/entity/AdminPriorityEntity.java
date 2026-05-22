package com.example.hospitalCrm.entity;

import com.example.hospitalCrm.type.Priority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminPriorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="admin_id", unique = true, nullable = false)
    private UsersEntity adminId;

    private String msg;

    @Enumerated(EnumType.STRING)
    private Priority priority;


}
