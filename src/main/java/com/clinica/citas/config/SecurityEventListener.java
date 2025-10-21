package com.clinica.citas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import com.clinica.citas.service.impl.AuditoriaServiceImpl;

@Component
@RequiredArgsConstructor
public class SecurityEventListener {

    private final AuditoriaServiceImpl auditoriaService;

    @EventListener
    public void handleLoginSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        auditoriaService.registrar("LOGIN", "Usuario", "Inicio de sesión exitoso de: " + username);
    }

    @EventListener
    public void handleLogout(LogoutSuccessEvent event) {
        String username = event.getAuthentication().getName();
        auditoriaService.registrar("LOGOUT", "Usuario", "Cierre de sesión de: " + username);
    }

    @EventListener
    public void handleFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        auditoriaService.registrar("LOGIN_FAIL", "Usuario", "Fallo de login para: " + username);
    }
}
