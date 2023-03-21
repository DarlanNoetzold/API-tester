package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.TestDeleteRequisition;
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.repository.TestDeleteRequisitionRepository;
import tech.noetzold.APItester.repository.UserRepository;

@Service
public class TestDeleteRequisitionService {

    @Autowired
    TestDeleteRequisitionRepository testDeleteRequisitionRepository;

    @Autowired
    UserRepository userRepository;

    public TestDeleteRequisition saveService(TestDeleteRequisition testDeleteRequisition){
        return testDeleteRequisitionRepository.save(testDeleteRequisition);
    }

    public Page<TestDeleteRequisition> findByUser(Pageable pageable, String login){
        User user = userRepository.findByLogin(login).get();
        return testDeleteRequisitionRepository.findByUser(pageable, user);
    }

    public Page<TestDeleteRequisition> findAll(Pageable pageable){
        return testDeleteRequisitionRepository.findAll(pageable);
    }

    public void deleteDeleteRequisitionById(Integer id) {
        testDeleteRequisitionRepository.deleteById(id);
    }
}
