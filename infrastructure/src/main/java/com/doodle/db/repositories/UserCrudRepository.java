package com.doodle.db.repositories;

import com.doodle.db.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<UserEntity, UUID> {
}
