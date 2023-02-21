package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.repository.RequisitionRepository;

@Service
public class RequisitionService {

    @Autowired
    RequisitionRepository requisitionRepository;
}
