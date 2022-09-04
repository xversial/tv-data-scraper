package com.duvallwifi.quickbooks.payment.dao;

import com.duvallwifi.quickbooks.payment.model.user.UserEntity;
import com.duvallwifi.quickbooks.payment.model.user.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, UserEntity user);

}
