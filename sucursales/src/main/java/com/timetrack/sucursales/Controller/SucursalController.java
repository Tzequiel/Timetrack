package com.timetrack.sucursales.Controller;

import com.timetrack.sucursales.Model.Sucursal;
import com.timetrack.sucursales.Service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @PostMapping
    public Sucursal registrarSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.crear(sucursal);
    }

    @GetMapping
    public List<Sucursal> verTodas() {
        return sucursalService.listarTodas();
    }

    @PutMapping("/{branchId}")
    public Sucursal actualizarSucursal(@PathVariable Long branchId, @RequestBody Sucursal sucursalDetalles) {
        return sucursalService.actualizar(branchId, sucursalDetalles);
    }
    @GetMapping("/{branchId}")
    public Sucursal obtenerPorId(@PathVariable Long branchId) {
        return sucursalService.obtenerPorId(branchId);
    }

    @DeleteMapping("/{branchId}")
    public void eliminarSucursal(@PathVariable Long branchId) {
        sucursalService.eliminar(branchId);
    }
}