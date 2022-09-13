package com.vionox.tools.tvscraper.dao;

import com.vionox.tools.tvscraper.model.user.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}
