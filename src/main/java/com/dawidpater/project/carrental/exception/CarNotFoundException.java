package com.dawidpater.project.carrental.exception;

import lombok.NoArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CarNotFoundException extends RuntimeException {
}
