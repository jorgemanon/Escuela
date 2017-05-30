package com.jje.programacion.escuela.utilerias;

public class Alumno extends Item{
    private String id, nombre, carrera, semestre, foto;

    public Alumno(){

    }

    public Alumno(String id, String nombre,String carrera,String semestre, String foto){
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.semestre = semestre;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCarrera() {
        return carrera;
    }
    public String getSemestre() {
        return semestre;
    }
    public String getFoto() {
        return foto;
    }

    public String toString(){
        return "ID: "+nombre+"\nCARRERA: "+carrera+"\nSEMESTRE: "+semestre;
    }
}
