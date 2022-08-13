package com.dawidpater.project.carrental.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IncorrectDateFormatException extends IllegalArgumentException {
}
