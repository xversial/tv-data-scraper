package com.duvallwifi.quickbooks.payment.dao;

import com.duvallwifi.quickbooks.payment.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< UserEntity, Long >
{
    public UserEntity findByEmail(String email);

    @Override
    void delete(UserEntity user);
}
