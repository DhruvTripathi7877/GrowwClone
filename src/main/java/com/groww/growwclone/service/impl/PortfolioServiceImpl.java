package com.groww.growwclone.service.impl;

import com.groww.growwclone.entity.Holding;
import com.groww.growwclone.repository.HoldingRepository;
import com.groww.growwclone.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final HoldingRepository holdingRepository;

    @Override
    public List<Holding> getPortfolioByUserId(Long userId)
    {
        return holdingRepository.findByUser_UserId(userId);
    }
}
