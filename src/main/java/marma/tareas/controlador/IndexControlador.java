package marma.tareas.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import marma.tareas.modelo.Tarea;
import marma.tareas.servicio.TareaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;


@Component
public class IndexControlador implements Initializable {
    private static  final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private TareaServicio tareaServicio;

    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea, Integer> idTareaColumn;

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumn;

    @FXML
    private TableColumn<Tarea, String> responsableColumn;

    @FXML
    private TableColumn<Tarea, String> estatusColumn;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    @FXML
    private TextField nombreTareaTexto;
    @FXML
    private TextField responsableTexto;
    @FXML
    private TextField estatusTexto;
    private Integer idTareainterno;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();

    }
    private void configurarColumnas(){
        idTareaColumn.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumn.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumn.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estatusColumn.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }
    private void listarTareas(){
        logger.info("Ejecutando listado de tareas");
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());
        tareaTabla.setItems(tareaList);
    }
    public void agregarTarea(){
        if (nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion", "Debe Proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        else {
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tarea.setIdTarea(null);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea Agregada");
            limpiarFormulario();
            listarTareas();
        }
    }
    public void cargarTareaFormulario(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItems();
        if (tarea != null){
            idTareainterno = tarea.getFirst().getIdTarea();
            nombreTareaTexto.setText(tarea.getFirst().getNombreTarea());
            responsableTexto.setText(tarea.getFirst().getResponsable());
            estatusTexto.setText(tarea.getFirst().getEstatus());
        }
    }
    private void mostrarMensaje(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    private void recolectarDatosFormulario(Tarea tarea){
        if (idTareainterno != null) {
            tarea.setIdTarea(idTareainterno);
            tarea.setNombreTarea(nombreTareaTexto.getText());
            tarea.setResponsable(responsableTexto.getText());
            tarea.setEstatus(estatusTexto.getText());
        }
    }

    public void modificarTarea(){
        if(idTareainterno ==null){
            mostrarMensaje("Informacion", "Debe seleccionar una tarea");
            return;
        }
        if (nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        var tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Informacion", "Tarea modificada");
        limpiarFormulario();
        listarTareas();
    }
    public void eliminarTarea (){
          var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if (tarea != null){
            logger.info("Registro a eliminar:" +tarea.toString());
            tareaServicio.eliminarTarea(tarea);

            mostrarMensaje("Informacion", "Tarea eliminada:" + tarea.getIdTarea());
            limpiarFormulario();
            listarTareas();
        }
        else {
            mostrarMensaje("Error", "No se ha seleccionada ninguna tarea");
        }
    }
    public void limpiarFormulario(){
        idTareainterno = null;
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }
}
