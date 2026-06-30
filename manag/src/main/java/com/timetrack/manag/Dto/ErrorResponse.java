package com.timetrack.manag.Dto;

import java.time.LocalDateTime;
import lombok.Data;
@Data
public class ErrorResponse {
    public String mensaje;
    public String detalle;
    public int status;
    public LocalDateTime timeStamp;
}