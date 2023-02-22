package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.TestGetRequisition;
import tech.noetzold.APItester.repository.TestGetRequisitionRepository;

@Service
public class TestGetRequisitionService {

    @Autowired
    TestGetRequisitionRepository testGetRequisitionRepository;

    public TestGetRequisition saveService(TestGetRequisition testGetRequisition){
        return testGetRequisitionRepository.save(testGetRequisition);
    }
}
