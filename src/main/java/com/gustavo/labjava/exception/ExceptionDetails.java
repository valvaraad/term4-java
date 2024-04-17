package com.gustavo.labjava.exception;

import java.util.Date;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
public class ExceptionDetails {
    private Date timestamp;
    private String message;
    private String details;
}