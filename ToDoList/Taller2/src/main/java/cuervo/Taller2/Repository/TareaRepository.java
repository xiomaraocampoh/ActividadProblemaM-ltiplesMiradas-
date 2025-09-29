package cuervo.Taller2.Repository;

import cuervo.Taller2.Modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {}
