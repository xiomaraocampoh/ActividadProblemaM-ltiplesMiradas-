package cuervo.Taller2.Servicio;

import cuervo.Taller2.Modelo.Tarea;
import cuervo.Taller2.Repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Transactional
    public List<Tarea> findAllTareas() {
        return tareaRepository.findAll();
    }

    @Transactional
    public Optional<Tarea> findTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    @Transactional
    public Tarea guardar(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarea no encontrado con ID: " + id);
        }
        tareaRepository.deleteById(id);
    }

    @Transactional
    public Tarea actualizarTarea(Long id, Tarea tareaActualizada) {
        return tareaRepository.findById(id)
                .map(empresaExistente -> {
                    // Actualizar campos
                    empresaExistente.setNombre(tareaActualizada.getNombre());
                    empresaExistente.setDescripcion(tareaActualizada.getDescripcion());
                    return tareaRepository.save(empresaExistente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + id));
    }



}
