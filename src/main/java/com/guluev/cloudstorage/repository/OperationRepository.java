package com.guluev.cloudstorage.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class OperationRepository {
    private final LoggerService loggerService;
    private ConcurrentMap<Integer, String> operationMap = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger();

    public void addSuccessfulOperation(String operationName) {
        operationMap.put(count.getAndAdd(1), operationName);
        loggerService.logSuccessfullyOperation(operationName);
    }

    public void addExceptionOperation(String operationName) {
        operationMap.put(count.getAndAdd(1), operationName);
        loggerService.logException(operationName);
    }

    public Map<Integer, String> getMap() {
        return operationMap;
    }

    public int getCount(){
        return count.get();
    }

}