package marma.tareas.servicio;

import marma.tareas.modelo.Tarea;

import java.util.List;

public interface ITareaservicio {
    public List<Tarea> listarTareas();
    public Tarea buscarTareaPorId(Integer idTarea);
    public void  guardarTarea(Tarea tarea);
    public  void eliminarTarea(Tarea tarea);

}
