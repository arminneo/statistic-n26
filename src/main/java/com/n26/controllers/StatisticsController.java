package com.n26.controllers;

import com.n26.io.Statistics;
import com.n26.services.StatisticsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {
    private final StatisticsServiceImpl statisticsService;

    public StatisticsController(StatisticsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}
