package com.guluev.cloudstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggerService {
    public void logException(String exception) {
        log.error(exception);
    }
    public void logSuccessfullyOperation(String operation){
        log.info(operation);

    }
}
