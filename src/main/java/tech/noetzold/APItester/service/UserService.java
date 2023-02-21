package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
}
