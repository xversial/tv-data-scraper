package com.vionox.tools.tvscraper.accounting.service;

import com.vionox.tools.tvscraper.accounting.exceptions.UserAlreadyExistException;
import com.vionox.tools.tvscraper.accounting.service.contracts.IUserService;
import com.vionox.tools.tvscraper.accounting.util.AuthorityHelper;
import com.vionox.tools.tvscraper.dao.*;
import com.vionox.tools.tvscraper.dto.UserDto;
import com.vionox.tools.tvscraper.model.user.*;
import com.vionox.tools.tvscraper.model.user.device.NewLocationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserService implements IUserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SessionRegistry sessionRegistry;

//    @Autowired
//    @Qualifier("GeoIPCountry")
//    private DatabaseReader databaseReader;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private NewLocationTokenRepository newLocationTokenRepository;

    @Autowired
    private Environment env;

    @Value("${site.base.url.https}")
    private String baseURL;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";
    private static final String ROLE_INACTIVE = "ROLE_INACTIVE";
    private static final String ROLE_ACTIVE = "ROLE_ACTIVATED";

    // API
    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email)!=null ? true : false;
    }

    @Override
    public UserEntity registerNewUserAccount(final UserDto accountDto) {
        if (checkIfUserExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final UserEntity user = new UserEntity();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setLot(accountDto.getLot());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
//        user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(this.defaultRegistrationRoles());
        return userRepository.save(user);
    }

    private Role createInactiveRole()
    {
        Role role = new Role(ROLE_INACTIVE);
        return roleRepository.save(role);
    }

    private Role createRole(String role)
    {
        Role newRole = new Role(role);
        return roleRepository.save(newRole);
    }

    public Role getOrCreateInactiveRole()
    {
        return getOrCreateRole(ROLE_INACTIVE);
    }
    public Role getOrCreateActivatedRole()
    {
        return getOrCreateRole(ROLE_ACTIVE);
    }
    public Role getOrCreateRole(String role)
    {
        Role roleSearch = roleRepository.findByName(role);
        if(roleSearch==null){
            Role newRole = new Role(role);
            roleSearch = roleRepository.save(newRole);
        }
        return roleSearch;
    }

    /**
     * @param user 
     * @return
     */
    @Override
    public boolean activateUser(final UserEntity user)
    {
        boolean status = false;
        boolean status2 = false;
        for (Role role : activatedRoles())
        {
            if(!AuthorityHelper.hasAuthority(user, role.getName()))
            {
                status = user.addRole(role);
            }
        }
        for (Role role : defaultRegistrationRoles())
        {
            status2 = user.removeRole(role);
        }
        if(status || status2){
            user.setRadiusActive(true);
            user.setActive(true);
            userRepository.save(user);
            updateRoles(user);
        }
        return status || status2;
    }

    @Transactional
    public void updateRoles(UserEntity user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final UserEntity latest = userRepository.findByEmail(auth.getName());
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(latest.getAuthorities());
//        updatedAuthorities.add(user.getAuthorities()); //add your role here [e.g., new SimpleGrantedAuthority("ROLE_NEW_ROLE")]

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public Collection<Role> defaultRegistrationRoles()
    {
        return Arrays.asList(getOrCreateInactiveRole());
    }

    public Collection<Role> activatedRoles()
    {
        return Arrays.asList(getOrCreateActivatedRole());
    }

    @Override
    public UserEntity getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(final UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final UserEntity user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        userRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final UserEntity user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final UserEntity user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public UserEntity findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public Optional<UserEntity> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    @Override
    public Optional<UserEntity> getUserByID(final long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserPassword(final UserEntity user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final UserEntity user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final UserEntity user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                     .getTime() - cal.getTime()
                     .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public String generateQRUrl(UserEntity user) throws UnsupportedEncodingException {
        return "";
//        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
    }
    @Override
    public UserEntity updateUser2FA(boolean use2FA) {
        final Authentication curAuth = SecurityContextHolder.getContext()
                .getAuthentication();
        UserEntity currentUser = (UserEntity) curAuth.getPrincipal();
        return currentUser;
    }

//    @Override
//    public String generateQRUrl(UserEntity user) throws UnsupportedEncodingException {
//        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
//    }

//    @Override
//    public UserEntity updateUser2FA(boolean use2FA) {
//        final Authentication curAuth = SecurityContextHolder.getContext()
//                .getAuthentication();
//        UserEntity currentUser = (UserEntity) curAuth.getPrincipal();
//        currentUser.setUsing2FA(use2FA);
//        currentUser = userRepository.save(currentUser);
//        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
//        SecurityContextHolder.getContext()
//                .setAuthentication(auth);
//        return currentUser;
//    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals()
                .stream()
                .filter((u) -> !sessionRegistry.getAllSessions(u, false)
                        .isEmpty())
                .map(o -> {
                    if (o instanceof UserEntity) {
                        return ((UserEntity) o).getEmail();
                    } else {
                        return o.toString()
                                ;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public NewLocationToken isNewLoginLocation(String username, String ip) {

        if(!isGeoIpLibEnabled()) {
            return null;
        }

        try {
            final InetAddress ipAddress = InetAddress.getByName(ip);
//            final String country = databaseReader.country(ipAddress)
//                    .getCountry()
//                    .getName();
            final String country = "United States";
            System.out.println(country + "====****");
            final UserEntity user = userRepository.findByEmail(username);
            final UserLocation loc = userLocationRepository.findByCountryAndUser(country, user);
            if ((loc == null) || !loc.isEnabled()) {
                return createNewLocationToken(country, user);
            }
        } catch (final Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String isValidNewLocationToken(String token) {
        final NewLocationToken locToken = newLocationTokenRepository.findByToken(token);
        if (locToken == null) {
            return null;
        }
        UserLocation userLoc = locToken.getUserLocation();
        userLoc.setEnabled(true);
        userLoc = userLocationRepository.save(userLoc);
        newLocationTokenRepository.delete(locToken);
        return userLoc.getCountry();
    }

    @Override
    public void addUserLocation(UserEntity user, String ip) {

        if(!isGeoIpLibEnabled()) {
            return;
        }

        try {
            final InetAddress ipAddress = InetAddress.getByName(ip);
//            final String country = databaseReader.country(ipAddress)
//                    .getCountry()
//                    .getName();
            final String country = "United States";
            UserLocation loc = new UserLocation(country, user);
            loc.setEnabled(true);
            userLocationRepository.save(loc);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isGeoIpLibEnabled() {
        return Boolean.parseBoolean(env.getProperty("geo.ip.lib.enabled"));
    }

    private NewLocationToken createNewLocationToken(String country, UserEntity user) {
        UserLocation loc = new UserLocation(country, user);
        loc = userLocationRepository.save(loc);

        final NewLocationToken token = new NewLocationToken(UUID.randomUUID()
                .toString(), loc);
        return newLocationTokenRepository.save(token);
    }
}
