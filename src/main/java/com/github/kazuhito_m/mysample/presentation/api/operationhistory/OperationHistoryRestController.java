package com.github.kazuhito_m.mysample.presentation.api.operationhistory;

import com.github.kazuhito_m.mysample.application.service.operationhistory.OperationHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/operationhistory")
public class OperationHistoryRestController {
    final OperationHistoryService service;

    @GetMapping("")
    List<OperationHistoryResponse> users() {
        return service.allOperationHistories()
                .list()
                .stream()
                .map(OperationHistoryResponse::new)
                .collect(toList());
    }

    OperationHistoryRestController(OperationHistoryService service) {
        this.service = service;
    }
}
