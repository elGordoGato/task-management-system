package org.elgordogato.taskmanagementsystem.services.userService;

import org.elgordogato.taskmanagementsystem.dtos.UserIdNameDto;
import org.elgordogato.taskmanagementsystem.entities.UserEntity;
import org.elgordogato.taskmanagementsystem.exceptions.NotFoundException;
import org.elgordogato.taskmanagementsystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity user;
    private UserIdNameDto userDto;
    private Pageable page;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setFullName("User 1");
        user.setEmail("user1@example.com");

        userDto = new UserIdNameDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getFullName() {
                return "User 1";
            }
        };

        page = Pageable.unpaged();
    }

    @Test
    public void testGetByIdSuccess() {
        // given
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // when
        UserEntity result = userService.getById(1L);

        // then
        verify(userRepository).findById(1L);
        assertEquals(user, result);
    }

    @Test
    public void testGetByIdNotFound() {
        // given
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getById(1L));

        // then
        verify(userRepository).findById(1L);
        assertEquals("UserEntity with id: 1 was not found", exception.getMessage());
    }

    @Test
    public void testGetAllShortSuccess() {
        // given
        List<UserIdNameDto> users = new ArrayList<>();
        users.add(userDto);
        Page<UserIdNameDto> expectedPage = new PageImpl<>(users, page, 1);
        when(userRepository.findAllProjectedBy(page)).thenReturn(expectedPage);

        // when
        Page<UserIdNameDto> resultPage = userService.getAllShort(page);

        // then
        verify(userRepository).findAllProjectedBy(page);
        assertEquals(expectedPage, resultPage);
    }
}