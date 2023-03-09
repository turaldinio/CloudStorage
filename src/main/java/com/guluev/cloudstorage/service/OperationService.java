package com.guluev.cloudstorage.service;

import com.guluev.cloudstorage.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    public void addSuccessfulOperation(String operation) {
        operationRepository.addSuccessfulOperation(operation);
    }

    public void addExceptionOperation(String exception) {
        operationRepository.addExceptionOperation(exception);
    }

    public Map<Integer, String> getMap() {
        return operationRepository.getMap();
    }

    public int getCount() {
        return   operationRepository.getCount();
    }
}