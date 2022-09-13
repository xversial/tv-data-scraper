package com.vionox.tools.tvscraper.dao;

import com.vionox.tools.tvscraper.model.user.UserLocation;
import com.vionox.tools.tvscraper.model.user.device.NewLocationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
