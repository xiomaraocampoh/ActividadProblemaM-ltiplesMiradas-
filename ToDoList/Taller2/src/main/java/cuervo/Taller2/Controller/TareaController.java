package cuervo.Taller2.Controller;

import cuervo.Taller2.Modelo.Tarea;
import cuervo.Taller2.Servicio.TareaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
public class TareaController {


    @Autowired
    TareaService tareaService;


    @GetMapping()
    public ArrayList<Tarea> findAll() {
        return (ArrayList<Tarea>) tareaService.findAllTareas();
    }

    @PostMapping("/crear")
    public Tarea guardarTarea(@RequestBody Tarea tarea) {
        return tareaService.guardar(tarea);
    }

    @GetMapping(path = "/{id}")
    public Optional<Tarea> findTareaById(@PathVariable Long id) {
        return this.tareaService.findTareaById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        Optional<Tarea> tareaExistente = tareaService.findTareaById(id);

        if (tareaExistente.isPresent()) {
            tarea.setId(id); // Asegurar que el ID sea el mismo
            Tarea actualizado = tareaService.guardar(tarea);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarTarea(@PathVariable Long id) {
        try {
            tareaService.eliminar(id);
            return new ResponseEntity<>(Map.of("eliminado", true), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
