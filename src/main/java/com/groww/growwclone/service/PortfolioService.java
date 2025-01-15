package com.groww.growwclone.service;

import com.groww.growwclone.entity.Holding;

import java.util.List;

public interface PortfolioService {
    public List<Holding> getPortfolioByUserId(Long userId);
}
