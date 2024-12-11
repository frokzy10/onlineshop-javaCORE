package application.service;

import application.entity.UserEntity;

import java.util.List;
import java.util.Objects;

public interface UserService {
    UserEntity userSave(UserEntity user);
    void logout();
    void user_info();
    UserEntity login(String email, String password);
}