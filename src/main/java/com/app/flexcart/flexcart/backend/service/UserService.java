package com.app.flexcart.flexcart.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.app.flexcart.flexcart.backend.domain.user.UserType;
import com.app.flexcart.flexcart.backend.model.entity.UserEntity;
import com.app.flexcart.flexcart.backend.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final OrderService orderService;

    private final UserRepository userRepository;

    public UserType getUserType(Long userId) {
        if (orderService.getUserPaymentLastYear(userId).compareTo(BigDecimal.valueOf(1000)) > 0) {
            return UserType.PREMIUM;
        } else if (userRepository.findById(userId).orElseThrow().getRegisterDate()
                .isAfter(LocalDateTime.now().minusMonths(1))) {
            return UserType.NEW;
        } else {
            return UserType.STANDARD;
        }
    }

    public void saveUser(String name, String surname) {
        var user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setRegisterDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
