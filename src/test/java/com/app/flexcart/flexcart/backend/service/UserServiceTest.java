package com.app.flexcart.flexcart.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.app.flexcart.flexcart.backend.domain.user.UserType;
import com.app.flexcart.flexcart.backend.exception.UserNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.OrderEntity;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.OrderRepository;
import com.app.flexcart.flexcart.backend.model.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    @Spy
    @InjectMocks
    private UserService userService;

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveUser_shouldSetNameSurnameAndRegisterDate() {
        String name = "John";
        String surname = "Doe";
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.saveUser(name, surname);

        verify(userRepository).save(captor.capture());
        UserEntity saved = captor.getValue();
        assertEquals(name, saved.getName());
        assertEquals(surname, saved.getSurname());
        assertNotNull(saved.getRegisterDate());
        assertTrue(saved.getRegisterDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void getUserEntityById_whenExists_shouldReturnEntity() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserEntityById(userId);

        assertSame(user, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserEntityById_whenNotFound_shouldThrow() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserEntityById(userId));
    }

    @Test
    void isTotalPaymentMoreThan_returnsTrueIfAboveThreshold() {
        OrderEntity o1 = new OrderEntity();
        o1.setTotal(new BigDecimal("6000"));
        o1.setDiscount(new BigDecimal("0"));
        when(orderRepository.findByUser_UserIdAndOrderDateAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(List.of(o1));

        assertTrue(userService.isTotalPaymentMoreThan(userId, BigDecimal.valueOf(5000)));
    }

    @Test
    void isTotalPaymentMoreThan_returnsFalseIfBelowThreshold() {
        OrderEntity o1 = new OrderEntity();
        o1.setTotal(new BigDecimal("3000"));
        o1.setDiscount(new BigDecimal("500"));
        when(orderRepository.findByUser_UserIdAndOrderDateAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(List.of(o1));

        assertFalse(userService.isTotalPaymentMoreThan(userId, BigDecimal.valueOf(5000)));
    }

    @Test
    void getUserType_premium() {
        OrderEntity o1 = new OrderEntity();
        o1.setTotal(new BigDecimal("6000"));
        o1.setDiscount(new BigDecimal("0"));
        when(orderRepository.findByUser_UserIdAndOrderDateAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(List.of(o1));

        UserType type = userService.getUserType(userId);
        assertEquals(UserType.PREMIUM, type);
    }

    @Test
    void getUserType_new() {
        when(orderRepository.findByUser_UserIdAndOrderDateAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(List.of());
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setRegisterDate(LocalDateTime.now().minusDays(10));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserType type = userService.getUserType(userId);
        assertEquals(UserType.NEW, type);
    }

    @Test
    void getUserType_standard() {
        when(orderRepository.findByUser_UserIdAndOrderDateAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(List.of());
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setRegisterDate(LocalDateTime.now().minusMonths(2));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserType type = userService.getUserType(userId);
        assertEquals(UserType.STANDARD, type);
    }

    @Test
    void getUserFromEntity_shouldMapFieldsCorrectly() {
        doReturn(UserType.PREMIUM).when(userService).getUserType(userId);
        doReturn(5L).when(userService).getOrderCountByUserId(userId);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);

        var domain = userService.getUserFromEntity(userEntity);
        assertEquals(userId, domain.getUserId());
        assertEquals(UserType.PREMIUM, domain.getUserType());
        assertEquals(5L, domain.getOrderCount());
    }

    @Test
    void getOrderCountByUserId_shouldCallRepository() {
        when(orderRepository.countByUser_UserId(userId)).thenReturn(42L);
        long count = userService.getOrderCountByUserId(userId);
        assertEquals(42L, count);
        verify(orderRepository).countByUser_UserId(userId);
    }
}