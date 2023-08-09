package com.it.serviceplatformbackend.service.query;

import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.dto.UserStatusResponse;
import com.it.serviceplatformbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;
    public List<UserStatusResponse> getAllRegularUsers() {
        List<User> users = userRepository.findAllRegularUsers();
        return users.stream()
                .map(user -> UserStatusResponse.builder()
                        .id(user.getId())
                        .active(user.isActive())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}
