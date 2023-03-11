package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.TestPutRequisition;
import tech.noetzold.APItester.repository.TestPutRequisitionRepository;

@Service
public class TestPutRequisitionService {

    @Autowired
    TestPutRequisitionRepository testPutRequisitionRepository;

    @Autowired
    UserService userService;

    public TestPutRequisition saveService(TestPutRequisition testPutRequisition){
        return testPutRequisitionRepository.save(testPutRequisition);
    }

    public Page<TestPutRequisition> findAll(Pageable pageable){
        return testPutRequisitionRepository.findAll(pageable);
    }

    public Page<TestPutRequisition> findByUser(Pageable pageable, String login){
        return testPutRequisitionRepository.findByUser(pageable, login);
    }

    public void deleteGetRequisitionById(Integer id) {
        testPutRequisitionRepository.deleteById(id);
    }
}
