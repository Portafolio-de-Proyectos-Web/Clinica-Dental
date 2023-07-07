
package org.josueperez.bean;


public class Especialidad  {
    private int codigoEspecialidad;
    private String descripcion;
    
    
    public Especialidad (){
    
    }

    public Especialidad(int codigoEspecialidad, String descripcion) {
        this.codigoEspecialidad = codigoEspecialidad;
        this.descripcion = descripcion;
    }

    public int getCodigoEspecialidad() {
        return codigoEspecialidad;
    }

    public void setCodigoEspecialidad(int codigoEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getCodigoEspecialidad()+" | " + getDescripcion();
    }    
}
