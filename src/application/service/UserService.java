package application.service;

import application.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity userSave(UserEntity user);
    void logout();
    void user_info();
    List<UserEntity> getUserByParam(String data);
    UserEntity login(String email, String password);
}