package com.github.kazuhito_m.mysample.application.service;

import com.github.kazuhito_m.mysample.domain.model.user.User;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import com.github.kazuhito_m.mysample.domain.model.user.UserRepository;
import com.github.kazuhito_m.mysample.domain.model.user.UserSummaries;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;

    public User findById(UserIdentifier id) {
        return userRepository.findBy(id);
    }

    public Boolean isExist(User user) {
        return userRepository.isExist(user);
    }

    public UserSummaries allUsers() {
        inServicePrivateMethod("allUsers()から呼び出した引数");
        return userRepository.list();
    }

    private String inServicePrivateMethod(String hikisuu) {
        System.out.println("ローカルメソッドを呼び出した時。引数:" + hikisuu);
//        throw new RuntimeException("意図して投げた例外");
        return "戻り地として定義されてる値";
    }

    public User prototype() {
        return userRepository.prototype();
    }

    public void register(User user) {
        userRepository.register(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
