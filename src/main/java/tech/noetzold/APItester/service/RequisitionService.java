package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.Requisition;
import tech.noetzold.APItester.repository.RequisitionRepository;

@Service
public class RequisitionService {

    @Autowired
    RequisitionRepository requisitionRepository;

    public Requisition saveService(Requisition requisition){
        return requisitionRepository.save(requisition);
    }
}
