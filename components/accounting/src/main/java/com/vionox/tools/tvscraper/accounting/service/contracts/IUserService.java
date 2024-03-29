package com.vionox.tools.tvscraper.accounting.service.contracts;

import com.vionox.tools.tvscraper.dto.UserDto;
import com.vionox.tools.tvscraper.model.user.PasswordResetToken;
import com.vionox.tools.tvscraper.model.user.Role;
import com.vionox.tools.tvscraper.model.user.UserEntity;
import com.vionox.tools.tvscraper.model.user.VerificationToken;
import com.vionox.tools.tvscraper.model.user.device.NewLocationToken;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUserService
{
    boolean checkIfUserExist(String email);

    UserEntity registerNewUserAccount(UserDto accountDto);

    UserEntity getUser(String verificationToken);

    VerificationToken getVerificationToken(String VerificationToken);

    void saveRegisteredUser(UserEntity user);

    void deleteUser(UserEntity user);

    void createVerificationTokenForUser(UserEntity user, String token);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);

    void createPasswordResetTokenForUser(UserEntity user, String token);

    UserEntity findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<UserEntity> getUserByPasswordResetToken(String token);

    Optional<UserEntity> getUserByID(long id);

    void changeUserPassword(UserEntity user, String password);

    boolean checkIfValidOldPassword(UserEntity user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(UserEntity user) throws UnsupportedEncodingException;

    UserEntity updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

    NewLocationToken isNewLoginLocation(String username, String ip);

    String isValidNewLocationToken(String token);

    void addUserLocation(UserEntity user, String ip);

    Collection<Role> activatedRoles();

    Role getOrCreateRole(String role);

    boolean activateUser(UserEntity user);
}
