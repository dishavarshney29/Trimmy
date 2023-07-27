package com.github.dishavarshney.Trimmy.utils;

import com.github.dishavarshney.trimmy.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

    @Test
    public void testGetUserPrincipal() {
        User userDetails = new User("testuser", "testpassword", AuthorityUtils.createAuthorityList("ROLE_USER"));

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);

        // Set the Authentication in SecurityContextHolder
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        String userPrincipal = Utils.getUserPrincipal();

        assertEquals("testuser", userPrincipal);
    }

//    @Test
//    public void testGetUserPrincipalObject() {
//        User userDetails = new User("testuser", "testpassword", AuthorityUtils.createAuthorityList("ROLE_USER"));
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
//
//        // Set the Authentication in SecurityContextHolder
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(auth);
//        SecurityContextHolder.setContext(securityContext);
//
//        Users userPrincipalObject = Utils.getUserPrincipalObject();
//
//        assertNotNull(userPrincipalObject);
//        assertEquals("testuser", userPrincipalObject.getUsername());
//        assertNull(userPrincipalObject.getPassword());
//        assertNotNull(userPrincipalObject.getAuthorities());
//        assertEquals(1, userPrincipalObject.getAuthorities().size());
//        assertEquals("ROLE_USER", userPrincipalObject.getAuthorities().iterator().next().getAuthority());
//    }

    @Test
    public void testGetShortUrl() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/someapp"));
        when(request.getServletPath()).thenReturn("/someapp");

        String shortUrl = Utils.getShortUrl(request, "abc123");

        assertEquals("http://localhost:8080/r/abc123", shortUrl);
    }
}
