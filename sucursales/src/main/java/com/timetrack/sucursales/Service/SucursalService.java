package com.timetrack.sucursales.Service;

import com.timetrack.sucursales.Model.Sucursal;
import com.timetrack.sucursales.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public Sucursal crear(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public List<Sucursal> listarTodas() {
        return sucursalRepository.findAll();
    }

    public Sucursal actualizar(Long id, Sucursal sucursalDetalles) {
        Sucursal sucursal = sucursalRepository.findById(id).orElse(null);
        if (sucursal != null) {
            sucursal.setNombre(sucursalDetalles.getNombre());
            sucursal.setDireccion(sucursalDetalles.getDireccion());
            sucursal.setLatitudCentro(sucursalDetalles.getLatitudCentro());
            sucursal.setLongitudCentro(sucursalDetalles.getLongitudCentro());
            sucursal.setRadioToleranciaMetros(sucursalDetalles.getRadioToleranciaMetros());
            sucursal.setEmpresaId(sucursalDetalles.getEmpresaId());
            return sucursalRepository.save(sucursal);
        }
        return null;
    }
}