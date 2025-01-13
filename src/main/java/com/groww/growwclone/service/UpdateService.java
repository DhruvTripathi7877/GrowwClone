package com.groww.growwclone.service;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateService {
    void updateStocksFromCsv(MultipartFile file);
}
