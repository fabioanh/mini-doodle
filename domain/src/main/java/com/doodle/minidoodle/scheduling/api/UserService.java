package com.doodle.minidoodle.scheduling.api;

import com.doodle.minidoodle.scheduling.*;
import com.doodle.minidoodle.scheduling.annotations.DomainService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    User createUser();
}
