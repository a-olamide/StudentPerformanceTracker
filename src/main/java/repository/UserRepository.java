package repository;

import models.User;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
    boolean validateLogin(String username, String password);
}
