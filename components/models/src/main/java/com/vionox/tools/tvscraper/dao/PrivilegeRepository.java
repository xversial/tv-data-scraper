package com.vionox.tools.tvscraper.dao;

import com.vionox.tools.tvscraper.model.user.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}

