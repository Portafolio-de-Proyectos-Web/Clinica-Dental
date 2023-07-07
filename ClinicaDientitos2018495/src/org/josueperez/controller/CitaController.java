
package org.josueperez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.josueperez.bean.Cita;
import org.josueperez.bean.Doctor;
import org.josueperez.bean.Paciente;
import org.josueperez.db.Conexion;
import org.josueperez.system.Principal;

public class CitaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cita>listaCita;
    private ObservableList<Paciente>listaPaciente;
    private ObservableList<Doctor>listaDoctor;
    private DatePicker fCita;
    
    @FXML private TableView tblCitas;
    @FXML private TableColumn colCodigoCita;
    @FXML private TableColumn colFechaCita;
    @FXML private TableColumn colHoraCita;
    @FXML private TableColumn colTratamiento;
    @FXML private TableColumn colDescripCondActual;
    @FXML private TableColumn colCodigoPaciente;
    @FXML private TableColumn colNumeroColegiado;
    @FXML private TextField txtCodigoCita;
    @FXML private TextField txtHoraCita;
    @FXML private TextField txtTratamiento;       
    @FXML private TextField txtDescripCondActual;
    @FXML private GridPane grpFechas;
    @FXML private ComboBox cmbCodigoPaciente;
    @FXML private ComboBox cmbNumeroColegiado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        cargarDatos();
        cmbCodigoPaciente.setItems(getPaciente());
        cmbCodigoPaciente.setDisable(true);
        cmbNumeroColegiado.setItems(getDoctor());
        cmbNumeroColegiado.setDisable(true);
        fCita = new DatePicker(Locale.ENGLISH);
        fCita.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fCita.getCalendarView().todayButtonTextProperty().set("Today");
        fCita.getCalendarView().setShowWeeks(false);
        grpFechas.add(fCita, 1, 1);
        fCita.getStylesheets().add("/org/josueperez/resource/DatePicker.css");
    }
    
    public void cargarDatos(){
        tblCitas.setItems(getCitas());
        colCodigoCita.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("codigoCita"));
        colFechaCita.setCellValueFactory(new PropertyValueFactory<Cita, Date>("fechaCita"));
        colHoraCita.setCellValueFactory(new PropertyValueFactory<Cita, String>("horaCita"));
        colTratamiento.setCellValueFactory(new PropertyValueFactory<Cita, String>("tratamiento"));
        colDescripCondActual.setCellValueFactory(new PropertyValueFactory<Cita, String>("descripCondActual"));
        colCodigoPaciente.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("codigoPaciente"));
        colNumeroColegiado.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("numeroColegiado"));
    }
    
    public void seleccionarElemento(){
        if ((((Cita)tblCitas.getSelectionModel().getSelectedItem())) != null){
            txtCodigoCita.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoCita()));
            txtHoraCita.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getHoraCita()));
            txtTratamiento.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getTratamiento()));
            txtDescripCondActual.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getDescripCondActual()));
            fCita.selectedDateProperty().set(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getFechaCita());
            cmbCodigoPaciente.getSelectionModel().select(buscarPaciente(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoPaciente()));
            cmbNumeroColegiado.getSelectionModel().select(buscarColegiado(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getNumeroColegiado()));
            
        }else{
            JOptionPane.showMessageDialog(null, "Debe Seleccionar Un Elemento");
        }  
    }
    
    public Paciente buscarPaciente(int codigoPaciente){
        Paciente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPaciente(?)}");
            procedimiento.setInt(1, codigoPaciente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Paciente(registro.getInt("codigoPaciente"),
                                        registro.getString("nombresPaciente"),
                                        registro.getString("apellidosPaciente"),
                                        registro.getString("sexo"),
                                        registro.getDate("fechaNacimiento"),
                                        registro.getString("direccionPaciente"),
                                        registro.getString("telefonoPersonal"),
                                        registro.getDate ("fechaPrimeraVisita"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    } 
    
    public Doctor buscarColegiado(int numeroColegiado){
        Doctor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDoctores(?)}");
            procedimiento.setInt(1, numeroColegiado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Doctor(registro.getInt("numeroColegiado"),
                                        registro.getString("nombresDoctor"),
                                        registro.getString("apellidosDoctor"),
                                        registro.getString("telefonoContacto"),
                                        registro.getInt ("codigoEspecialidad"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    } 
    
    public ObservableList<Cita> getCitas(){
        ArrayList <Cita> lista = new ArrayList<Cita>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCita}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cita(resultado.getInt ("codigoCita"),
                                        resultado.getDate("fechaCita"),
                                        resultado.getString ("horaCita"),
                                        resultado.getString("tratamiento"),
                                        resultado.getString("descripCondActual"),
                                        resultado.getInt("codigoPaciente"),
                                        resultado.getInt("numeroColegiado")));
            }
        }catch (Exception e){
        e.printStackTrace();
        }
        return listaCita = FXCollections.observableArrayList(lista);
    }
    
    
    public ObservableList<Doctor> getDoctor(){
        ArrayList <Doctor> lista = new ArrayList<Doctor>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDoctores}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Doctor(resultado.getInt ("numeroColegiado"),
                                        resultado.getString("nombresDoctor"),
                                        resultado.getString("apellidosDoctor"),
                                        resultado.getString("telefonoContacto"),
                                        resultado.getInt ("codigoEspecialidad")));
            }
        }catch (Exception e){
        e.printStackTrace();
        }
        return listaDoctor = FXCollections.observableArrayList(lista);
    }
    
    
     public ObservableList<Paciente> getPaciente(){
        ArrayList<Paciente> lista = new ArrayList<Paciente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPacientes}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Paciente(resultado.getInt("codigoPaciente"),
                                    resultado.getString("nombresPaciente"),
                                    resultado.getString("apellidosPaciente"),
                                    resultado.getString("sexo"),
                                    resultado.getDate("fechaNacimiento"),
                                    resultado.getString("direccionPaciente"),
                                    resultado.getString("telefonoPersonal"),
                                    resultado.getDate("fechaPrimeraVisita")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaPaciente = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo (){
        switch (tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image ("/org/josueperez/image/Agregar.png"));
                imgEliminar.setImage(new Image ("/org/josueperez/image/Eliminar.png"));
                tipoDeOperacion = CitaController.operaciones.GUARDAR;
                break;
            case GUARDAR:
               guardar();
               desactivarControles();
               limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/josueperez/image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/josueperez/image/Eliminar.png"));
                tipoDeOperacion = CitaController.operaciones.NINGUNO;
                cargarDatos();
                break;  
        }
    }
    
    public void guardar(){
        Cita registro = new Cita();
        registro.setFechaCita(fCita.getSelectedDate());
        registro.setHoraCita(txtHoraCita.getText());
        registro.setTratamiento(txtTratamiento.getText());
        registro.setDescripCondActual(txtDescripCondActual.getText());
        registro.setCodigoPaciente(((Paciente)cmbCodigoPaciente.getSelectionModel().getSelectedItem()).getCodigoPaciente());
        registro.setNumeroColegiado(((Doctor)cmbNumeroColegiado.getSelectionModel().getSelectedItem()).getNumeroColegiado());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_AgregarCita(?,?,?,?,?,?)}");
            procedimiento.setDate(1,new java.sql.Date(registro.getFechaCita().getTime()));
            procedimiento.setString(2, registro.getHoraCita());
            procedimiento.setString(3, registro.getTratamiento());
            procedimiento.setString(4, registro.getDescripCondActual());
            procedimiento.setInt(5, registro.getCodigoPaciente());
            procedimiento.setInt(6, registro.getNumeroColegiado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("NUEVO");
                btnEliminar.setText("ELIMINAR");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/josueperez/image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/josueperez/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblCitas.getSelectionModel ().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null," ¿Está seguro de eliminar el registro?","Eliminar Cita",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                         try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCita(?)}");
                            procedimiento.setInt(1,((Cita) tblCitas.getSelectionModel().getSelectedItem()).getCodigoCita());
                            procedimiento.execute();
                            listaCita.remove(tblCitas.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                break;
                
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblCitas.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josueperez/image/Editar.png"));
                    imgReporte.setImage(new Image("/org/josueperez/image/cancelar.png"));
                    activarControles();
                    txtCodigoCita.setEditable(false);
                    cmbCodigoPaciente.setDisable(true);
                    cmbNumeroColegiado.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Dene seleccionar un elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/josueperez/image/Editar.png"));
                imgReporte.setImage(new Image("/org/josueperez/image/Reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCita(?,?,?,?,?)}");
            Cita registro = (Cita)tblCitas.getSelectionModel().getSelectedItem();
            registro.setFechaCita(fCita.getSelectedDate());
            registro.setHoraCita(txtHoraCita.getText());
            registro.setTratamiento(txtTratamiento.getText());
            registro.setDescripCondActual(txtDescripCondActual.getText());
            procedimiento.setInt(1, registro.getCodigoCita());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaCita().getTime()));
            procedimiento.setString(3,registro.getHoraCita());
            procedimiento.setString(4, registro.getTratamiento());
            procedimiento.setString(5, registro.getDescripCondActual());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/josueperez/image/Editar.png"));
                imgReporte.setImage(new Image("/org/josueperez/image/Reporte.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void desactivarControles(){
        txtCodigoCita.setDisable(true);
        txtHoraCita.setDisable(false);
        txtTratamiento.setDisable(false);
        txtDescripCondActual.setDisable(false);
        cmbCodigoPaciente.setDisable(true);
        cmbNumeroColegiado.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoCita.setDisable(true);
        txtHoraCita.setEditable(true);
        txtTratamiento.setEditable(true);
        txtDescripCondActual.setEditable(true);
        cmbCodigoPaciente.setDisable(false);
        cmbNumeroColegiado.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoCita.clear();
        txtHoraCita.clear();
        txtTratamiento.clear();
        txtDescripCondActual.clear();
        fCita.setSelectedDate(null);
        cmbCodigoPaciente.getSelectionModel().select(null);
        cmbNumeroColegiado.getSelectionModel().select(null);
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
