package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.noetzold.APItester.model.CompleteRequest;
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.repository.CompleteRequestRepository;
import tech.noetzold.APItester.repository.UserRepository;


public class CompleteRequestService {
    @Autowired
    CompleteRequestRepository completeRequestRepository;

    @Autowired
    UserRepository userRepository;

    public CompleteRequest saveService(CompleteRequest completeRequest){
        return completeRequestRepository.save(completeRequest);
    }

    public Page<CompleteRequest> findByUser(Pageable pageable, String login){
        User user = userRepository.findByLogin(login).get();
        return completeRequestRepository.findByUser(pageable, user);
    }

    public Page<CompleteRequest> findAll(Pageable pageable){
        return completeRequestRepository.findAll(pageable);
    }

    public void deleteFullPerformanceById(Integer id) {
        completeRequestRepository.deleteById(id);
    }
}
