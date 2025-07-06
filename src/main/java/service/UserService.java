package service;

import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

public class UserService {
    private final UserRepository userRepo = new UserRepositoryImpl();

    public boolean login(String username, String password) {
        return userRepo.validateLogin(username, password);
    }
}
