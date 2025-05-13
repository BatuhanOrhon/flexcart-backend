package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.controller.schema.UserResponse;
import com.app.flexcart.flexcart.backend.domain.user.User;
import com.app.flexcart.flexcart.backend.domain.user.UserType;
import com.app.flexcart.flexcart.backend.exception.UserNotFoundException;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.OrderRepository;
import com.app.flexcart.flexcart.backend.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    public UserType getUserType(Long userId) {
        if (isTotalPaymentMoreThan(userId, BigDecimal.valueOf(5000))) {
            return UserType.PREMIUM;
        } else if (isNewUser(userId)) {
            return UserType.NEW;
        } else {
            return UserType.STANDARD;
        }
    }

    public Boolean isTotalPaymentMoreThan(Long userId, BigDecimal amount) {
        return getUserPaymentLastYear(userId).compareTo(amount) > 0;
    }

    private BigDecimal getUserPaymentLastYear(Long userId) {
        var orders = orderRepository.findByUser_UserIdAndOrderDateAfter(userId, LocalDateTime.now().minusYears(1));
        return orders.stream()
                .map(o -> o.getTotal().subtract(o.getDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Boolean isNewUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId)).getRegisterDate()
                .isAfter(LocalDateTime.now().minusMonths(1));
    }

    public Long saveUser(String name, String surname) {
        var user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setRegisterDate(LocalDateTime.now());
        var savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }

    public UserEntity getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public User getUserFromEntity(UserEntity user) {
        var userType = getUserType(user.getUserId());
        var userDomain = new User();
        userDomain.setUserId(user.getUserId());
        userDomain.setUserType(userType);
        userDomain.setOrderCount(getOrderCountByUserId(user.getUserId()));
        return userDomain;
    }

    public long getOrderCountByUserId(Long userId) {
        return orderRepository.countByUser_UserId(userId);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            var response = new UserResponse();
            response.setId(user.getUserId());
            response.setName(user.getName());
            response.setSurname(user.getSurname());
            response.setRegisterDate(user.getRegisterDate());
            return response;
        }).toList();
    }
}
