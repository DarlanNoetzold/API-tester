package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.TestGetRequisition;
import tech.noetzold.APItester.model.TestPostRequisition;
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.repository.TestGetRequisitionRepository;
import tech.noetzold.APItester.repository.UserRepository;

@Service
public class TestGetRequisitionService {

    @Autowired
    TestGetRequisitionRepository testGetRequisitionRepository;

    @Autowired
    UserRepository userRepository;

    public TestGetRequisition saveService(TestGetRequisition testGetRequisition){
        return testGetRequisitionRepository.save(testGetRequisition);
    }

    public Page<TestGetRequisition> findByUser(Pageable pageable, String login){
        User user = userRepository.findByLogin(login).get();
        return testGetRequisitionRepository.findByUser(pageable, user);
    }

    public Page<TestGetRequisition> findAll(Pageable pageable){
        return testGetRequisitionRepository.findAll(pageable);
    }

    public void deleteGetRequisitionById(Integer id) {
        testGetRequisitionRepository.deleteById(id);
    }
}
