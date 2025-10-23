package org.userManagement.service;

import org.userManagement.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User userDetails);

    void deleteUser(Long id);

    Optional<User> getUserByEmail(String email);
}
