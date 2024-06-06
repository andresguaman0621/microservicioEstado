package com.evalueytor.estado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.evalueytor.estado.models.Estado;
import com.evalueytor.estado.repositories.EstadoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {
    @Autowired
    EstadoRepository estadoRepository;

    // Listar todo
    @GetMapping("/findall")
    public List<Estado> list() {
        return estadoRepository.findAll();
    }

    // Listar por Id
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Estado> obtenerPremioPorId(@PathVariable Long id) {
        Optional<Estado> premioOptional = estadoRepository.findById(id);
        return premioOptional.map(premio -> new ResponseEntity<>(premio, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva estado
    @PostMapping("/save")
    public ResponseEntity<Estado> crearPremio(@RequestBody Estado nuevoPremio) {
        Estado premioGuardado = estadoRepository.save(nuevoPremio);
        return new ResponseEntity<>(premioGuardado, HttpStatus.CREATED);
    }

    // Actualizar estado
    @PutMapping("/updatebyid/{id}")
    public ResponseEntity<Estado> actualizarEstado(@PathVariable Long id, @RequestBody Estado estadoActual) {
        Optional<Estado> estadoOptional = estadoRepository.findById(id);
        return estadoOptional.map(estado -> {
            estado.setId(id);
            estado.setDescripcion(estadoActual.getDescripcion());
            estado.setValor(estadoActual.getValor());
            Estado estadoActualGuardado = estadoRepository.save(estado);
            return new ResponseEntity<>(estadoActualGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un estado por ID
    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Long id) {
        Optional<Estado> estadoOptional = estadoRepository.findById(id);
        if (estadoOptional.isPresent()) {
            estadoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
