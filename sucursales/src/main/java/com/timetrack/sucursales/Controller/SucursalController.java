package com.timetrack.sucursales.Controller;

import com.timetrack.sucursales.Assemblers.SucursalesModelAssembler;
import com.timetrack.sucursales.Model.Sucursal;
import com.timetrack.sucursales.Service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/branches")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalesModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Sucursal>> registrarSucursal(@RequestBody Sucursal sucursal) {
        Sucursal creada = sucursalService.crear(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creada));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> verTodas() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalController.class).verTodas()).withSelfRel()));
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<EntityModel<Sucursal>> obtenerPorId(@PathVariable Long branchId) {
        Sucursal sucursal = sucursalService.obtenerPorId(branchId);
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(@PathVariable Long branchId, @RequestBody Sucursal sucursalDetalles) {
        Sucursal actualizada = sucursalService.actualizar(branchId, sucursalDetalles);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<String> eliminarSucursal(@PathVariable Long branchId) {
        sucursalService.eliminar(branchId);
        return ResponseEntity.ok("Sucursal eliminada correctamente.");
    }
}