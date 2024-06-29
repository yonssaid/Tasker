package com.Tasker.Services;

import com.Tasker.Models.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Tasker.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public MyUser save(MyUser myUser) {
        return userRepository.save(myUser);
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
