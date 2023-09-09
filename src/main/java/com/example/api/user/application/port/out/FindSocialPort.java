package com.example.api.user.application.port.out;

import com.example.api.user.adapter.out.persistence.UserEntity;

import java.util.Optional;

public interface FindSocialPort {
    Optional<UserEntity> findSocialUser(String id, String provider);
}
