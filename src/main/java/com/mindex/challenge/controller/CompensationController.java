package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.impl.CompensationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
    private final CompensationServiceImpl compensationService;

    public CompensationController(CompensationServiceImpl compensationService) {
        this.compensationService = compensationService;
    }

    @PutMapping("/compensation/{id}")
    public Compensation create(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee [{}]", id);

        return compensationService.create(id, compensation).getCompensation();
    }

    @GetMapping("/compensation/{id}")
    public Compensation readEmployee(@PathVariable String id) {
        LOG.debug("Received compensation read request for employeeId [{}]", id);

        return compensationService.read(id);
    }

}
