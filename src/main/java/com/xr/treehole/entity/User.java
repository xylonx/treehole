package com.xr.treehole.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    private String emailHash;

    @Transient
    private String emailAddress;

    @Transient
    private String userName;

    private String password;

}
