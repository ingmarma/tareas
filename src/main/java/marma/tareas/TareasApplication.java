package marma.tareas;

import javafx.application.Application;
import marma.tareas.presentacion.SistemaTareaFx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TareasApplication {

	public static void main(String[] args) {

//		SpringApplication.run(TareasApplication.class, args);
		Application.launch(SistemaTareaFx.class, args);
	}

}
