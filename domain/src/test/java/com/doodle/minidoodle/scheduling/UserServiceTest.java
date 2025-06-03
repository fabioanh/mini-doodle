package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.api.UserService;
import com.doodle.minidoodle.scheduling.spi.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void createUser_basicScenario_successful() {
        // given
        UserService userService = new UserServiceImpl(userRepository);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        // when
        User createdUser = userService.createUser();
        // then
        Assertions.assertNotNull(createdUser);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }
}