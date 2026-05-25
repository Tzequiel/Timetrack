package com.timetrack.attendance.Client;

import com.timetrack.attendance.Dto.LocationValidationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "location", url = "http://localhost:8085")
public interface LocationClient {

    @PostMapping("/api/location/validate")
    String validarUbicacion(@RequestBody LocationValidationDto request);
}