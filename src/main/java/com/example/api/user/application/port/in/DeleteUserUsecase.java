package com.example.api.user.application.port.in;

public interface DeleteUserUsecase {
    void deleteAll();
    void deleteUser(Long userId);
}