package tech.noetzold.APItester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.APItester.model.Requisition;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.repository.ResultRepository;

@Service
public class ResultService {

    @Autowired
    ResultRepository resultRepository;

    public Result saveService(Result result){
        return resultRepository.save(result);
    }

}
