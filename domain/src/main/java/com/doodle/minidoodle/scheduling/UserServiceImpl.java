package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.annotations.DomainService;
import com.doodle.minidoodle.scheduling.api.UserService;
import com.doodle.minidoodle.scheduling.spi.UserRepository;

@DomainService
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser() {
        return userRepository.save(new User());
    }
}
