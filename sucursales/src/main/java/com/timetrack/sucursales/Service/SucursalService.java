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
}