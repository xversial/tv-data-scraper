package com.duvallwifi.quickbooks.payment.dao;

import com.duvallwifi.quickbooks.payment.model.user.UserLocation;
import com.duvallwifi.quickbooks.payment.model.user.device.NewLocationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
