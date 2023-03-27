package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.FullPerformanceTest;
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.repository.FullPerformanceTestRepository;
import tech.noetzold.APItester.repository.UserRepository;

@Service
public class FullPerformanceTestService {

    @Autowired
    FullPerformanceTestRepository fullPerformanceTestRepository;

    @Autowired
    UserRepository userRepository;

    public FullPerformanceTest saveService(FullPerformanceTest fullPerformanceTest){
        return fullPerformanceTestRepository.save(fullPerformanceTest);
    }

    public Page<FullPerformanceTest> findByUser(Pageable pageable, String login){
        User user = userRepository.findByLogin(login).get();
        return fullPerformanceTestRepository.findByUser(pageable, user);
    }

    public Page<FullPerformanceTest> findAll(Pageable pageable){
        return fullPerformanceTestRepository.findAll(pageable);
    }

    public void deleteFullPerformanceById(Integer id) {
        fullPerformanceTestRepository.deleteById(id);
    }
}
