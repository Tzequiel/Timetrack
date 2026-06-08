package com.timetrack.attendance.Client;

import com.timetrack.attendance.Dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "manag", url = "http://localhost:8084")
public interface ManagClient {

    @GetMapping("/api/users/{userId}")
    UsuarioDto verPorId(@PathVariable("userId") Long userId);
}