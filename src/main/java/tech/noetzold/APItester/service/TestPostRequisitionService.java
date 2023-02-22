package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import tech.noetzold.APItester.model.TestPostRequisition;
import tech.noetzold.APItester.repository.TestPostRequisitionRepository;

public class TestPostRequisitionService {

    @Autowired
    TestPostRequisitionRepository testPostRequisitionRepository;

    public TestPostRequisition saveService(TestPostRequisition testPostRequisition){
        return testPostRequisitionRepository.save(testPostRequisition);
    }
}
