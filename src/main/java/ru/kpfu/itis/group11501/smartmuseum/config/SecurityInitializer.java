package ru.kpfu.itis.group11501.smartmuseum.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(2)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
