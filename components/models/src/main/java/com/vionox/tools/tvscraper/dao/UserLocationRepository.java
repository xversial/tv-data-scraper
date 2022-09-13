package com.vionox.tools.tvscraper.dao;

import com.vionox.tools.tvscraper.model.user.UserEntity;
import com.vionox.tools.tvscraper.model.user.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, UserEntity user);

}
