package com.LungCancerDetection.Security.Entity;

import com.LungCancerDetection.Security.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public String getAuthority(){
        return role.name();
    }
}
