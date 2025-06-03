package com.doodle.db.entities;

import com.doodle.minidoodle.scheduling.User;
import com.doodle.minidoodle.scheduling.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class UserEntity {
    @Id
    private UUID id;

    public UserEntity(UUID id) {
        this.id = id;
    }

    public UserEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User toDomain() {
        return new User(new UserId(id));
    }
}
