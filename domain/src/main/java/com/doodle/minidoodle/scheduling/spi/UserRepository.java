package com.doodle.minidoodle.scheduling.spi;

import com.doodle.minidoodle.scheduling.User;

public interface UserRepository {
    User save(User user);
}
