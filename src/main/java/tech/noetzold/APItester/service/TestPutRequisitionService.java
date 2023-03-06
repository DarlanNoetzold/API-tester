package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.repository.TestPutRequisitionRepository;

@Service
public class TestPutRequisitionService {

    @Autowired
    TestPutRequisitionRepository testPutRequisitionRepository;

    @Autowired
    UserService userService;
}
