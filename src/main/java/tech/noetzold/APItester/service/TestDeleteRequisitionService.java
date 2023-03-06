package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import tech.noetzold.APItester.repository.TestDeleteRequisitionRepository;

public class TestDeleteRequisitionService {

    @Autowired
    TestDeleteRequisitionRepository testDeleteRequisitionRepository;

    @Autowired
    UserService userService;
}
