package com.vionox.tools.tvscraper.accounting.seed;

import com.vionox.tools.tvscraper.accounting.service.UserService;
import com.vionox.tools.tvscraper.dao.UserRepository;
import com.vionox.tools.tvscraper.dto.UserDto;
import com.vionox.tools.tvscraper.model.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner
{
    private static final Logger LOG = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public DataSeeder(UserRepository userRepository, UserService userService)
    {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void run(ApplicationArguments args)
    {
        LOG.debug("Seeding database");
        seedUser("test", "test", "first", "last");
    }

    public void seedUser(String email, String password, String first, String last)
    {
        if (userService.checkIfUserExist(email))
            return;

        UserDto user = new UserDto();

        try
        {
            user.setEmail(email);
            user.setFirstName(first);
            user.setLastName(last);
            user.setPassword(password);
            user.setMatchingPassword(password);
            user.setLot(1);
        } finally
        {
            userService.registerNewUserAccount(user);
        }
    }
}
