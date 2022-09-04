package com.duvallwifi.quickbooks.payment.dao;

import com.duvallwifi.quickbooks.payment.model.user.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}
