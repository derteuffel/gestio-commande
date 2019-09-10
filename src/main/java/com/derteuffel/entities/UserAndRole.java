package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_and_role")
public class UserAndRole {

    @Id
    @GeneratedValue
    private int userAndRoleId;
    private int userId;
    private int roleId;


}
