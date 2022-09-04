package com.duvallwifi.quickbooks.payment.dao;

import com.duvallwifi.quickbooks.payment.model.user.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}

