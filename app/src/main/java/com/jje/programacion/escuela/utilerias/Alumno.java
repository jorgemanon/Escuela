package com.jje.programacion.escuela.utilerias;

public class Alumno extends Item{
    private String nombre, carrera, semestre, foto;

    public Alumno(){

    }

    public Alumno(String nombre,String carrera,String semestre, String foto){
        this.nombre = nombre;
        this.carrera = carrera;
        this.semestre = semestre;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
