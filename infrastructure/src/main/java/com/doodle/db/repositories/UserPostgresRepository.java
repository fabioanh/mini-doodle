package com.doodle.db.repositories;

import com.doodle.db.entities.UserEntity;
import com.doodle.minidoodle.scheduling.User;
import com.doodle.minidoodle.scheduling.spi.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserPostgresRepository implements UserRepository {
    private final UserCrudRepository userCrudRepository;

    public UserPostgresRepository(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    public User save(User user) {
        return this.userCrudRepository.save(new UserEntity(user.getUserId().getId())).toDomain();
    }
}
