package com.vionox.tools.tvscraper.model.user;

import lombok.*;
import org.apache.commons.text.WordUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "account_users")
public class UserEntity implements Serializable, UserDetails
{

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First name is required.")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Last name is required.")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull(message = "Email is required.")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Password is required.")
    private String password;

    @Column(name = "lot", nullable = true)
    private String lot;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "radius_active")
    @Getter
    @Setter
    private boolean radiusActive = false;
    @Column(name = "enabled")
    private boolean enabled = true;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Collection<Role> getRoles()
    {
        return roles;
    }

    public boolean addRole(Role role)
    {
        return roles.add(role);
    }

    public boolean removeRole(Role role)
    {
        return roles.remove(role);
    }

    public void setRoles(final Collection<Role> roles)
    {
        this.roles = roles;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getLot()
    {
        return lot;
    }

    public void setLot(final String lot)
    {
        this.lot = lot;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setId(int id)
    {
        this.id = Long.parseLong(String.valueOf(id));
    }

    public String getFirstNameDisplay()
    {
        return WordUtils.capitalize(this.getFirstName());
    }

    public String getLastNameDisplay()
    {
        return WordUtils.capitalize(this.getLastName());
    }

    public String getName()
    {
        return WordUtils.capitalize(this.getFirstName() + " " + this.getLastName());
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Country{");
        sb.append("id=").append(id);
        sb.append(", firstname='").append(firstName).append('\'');
        sb.append(", lastname='").append(lastName).append('\'');
        sb.append(", email=").append(email);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final UserEntity user = (UserEntity) obj;
        return getEmail().equals(user.getEmail());
    }

    public boolean isActive()
    {
        return active;
    }

    /**
     * @return <code>true</code> if allowed to connect using RADIUS
     */
    public boolean isRADIUSAllowed()
    {
        return this.radiusActive;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role :
                getRoles())
        {
            final Collection<Privilege> privileges = role.getPrivileges();
            if(privileges != null)
            {
                for (Privilege privilege : privileges)
                {
                    authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                }
            }
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public String getPassword()
    {
        return password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername()
    {
        return getEmail();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired()
    {
        return isActive();
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked()
    {
        return isEnabled();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }
}
