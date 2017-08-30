package com.jje.programacion.escuela.modelo;

public class Maestro extends Item {

    private String maestroId, nombre, apellidoPaterno, apellidoMaterno, foto;

    public Maestro(String maestroId, String nombre, String apellidoPaterno, String apellidoMaterno, String foto) {
        this.maestroId = maestroId;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.foto = foto;
    }

    public String getMaestroId() {return maestroId;}
    public String getNombre() {return nombre;}
    public String getApellidoPaterno() {return apellidoPaterno;}
    public String getApellidoMaterno() {return apellidoMaterno;}
    public String getFoto() {return foto;}

    @Override
    public String toString() {
        return "Maestro{" +
                "maestroId='" + maestroId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
