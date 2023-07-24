package com.github.dishavarshney.trimmy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dishavarshney.trimmy.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails, PrePersistListener, PreUpdateListener{

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private String token;

    private String active;

    private String createdBy;

    private String updatedBy;

    private Date createdAt;

    private Date updatedAt;

    @Version
    private Long version;

    private List<String> roles = new ArrayList<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void onPrePersist() {
        active = "Y";
        createdBy = Utils.getUserPrincipal();
        createdAt = Timestamp.from(Instant.now());
    }

    @Override
    public void onPreUpdate() {
        updatedBy = Utils.getUserPrincipal();
        updatedAt = Timestamp.from(Instant.now());
    }
}