/*
Nombre: Josue David Perez Cap
Codigo Tecnico: IN5AV
Carne: 2018-495
Fecha de Creacion: 05-04-2022
Modificaciones: 18-04-2022, 23-04-2022, 25-04-2022,03-05-2022, 09-05-2022 , 10-05-2022, 23-05-22 , 24-05-22, 28-05-22
 */
package org.josueperez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josueperez.controller.CitaController;
import org.josueperez.controller.DetalleRecetaController;
import org.josueperez.controller.DoctorController;
import org.josueperez.controller.EspecialidadController;
import org.josueperez.controller.LoginController;
import org.josueperez.controller.MedicamentoController;
import org.josueperez.controller.MenuPrincipalController;
import org.josueperez.controller.PacienteController;
import org.josueperez.controller.ProgramadorController;
import org.josueperez.controller.RecetaController;
import org.josueperez.controller.UsuarioController;


public class Principal extends Application {
    
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/josueperez/view/";
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Clinica Dientitos");
        escenarioPrincipal.getIcons().add(new Image("/org/josueperez/image/LogodeClinicaDientitos.png"));
//        Parent root = FXMLLoader.load(getClass().getResource("/org/josueperez/view/MenuPrincipalView.fxml"));
//        Scene escena = new Scene(root);
//        escenarioPrincipal.setScene(escena);
        
        ventanaLogin();
        escenarioPrincipal.show();
    }
    
    public void ventanaLogin() {
        try{
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml",700,480);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuarioView.fxml",835,400);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuPrincipal(){
       try{
            MenuPrincipalController ventanaMenu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",407,404);
            ventanaMenu.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController vistaProgramador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml",587,400);
            vistaProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPacientes(){
        try{
            PacienteController vistaPacientes = (PacienteController) cambiarEscena("PacienteView.fxml",1100,400);
            vistaPacientes.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaEspecialidades () {
        try{
            EspecialidadController vistaEspecialidad = (EspecialidadController)cambiarEscena("EspecialidadesView.fxml",836, 400);
            vistaEspecialidad.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaMedicamentos (){
        try{
            MedicamentoController vistaMedicamento = (MedicamentoController)cambiarEscena("MedicamentoView.fxml", 836, 400);
            vistaMedicamento.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaDoctores(){
        try{
            DoctorController vistaDoctor = (DoctorController)cambiarEscena("DoctorView.fxml",1100, 400);
            vistaDoctor.setEscenarioPrincipal(this); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaRecetas(){
        try{
            RecetaController vistaReceta = (RecetaController)cambiarEscena("RecetaView.fxml", 836,400);
            vistaReceta.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDetalleRecetas(){
        try{
            DetalleRecetaController vistaDetalleReceta = (DetalleRecetaController)cambiarEscena("DetalleRecetaView.fxml",923,400);
            vistaDetalleReceta.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaCitas(){
        try{
            CitaController vistaCita = (CitaController)cambiarEscena("CitaView.fxml",1100,400);
            vistaCita.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        
        return resultado;
    }
    
}
