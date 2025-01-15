package com.groww.growwclone.controller;


import com.groww.growwclone.entity.Holding;
import com.groww.growwclone.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("{userId}")
    public List<Holding> getPortfolio(@PathVariable Long userId)
    {
        return portfolioService.getPortfolioByUserId(userId);
    }
}
