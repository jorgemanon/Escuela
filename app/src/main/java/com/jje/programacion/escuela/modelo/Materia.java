package com.jje.programacion.escuela.modelo;

public class Materia extends Item {

    private String materiaNombre, maestroNombre, horario, aulaNombre;
    private double calificacion;

    public Materia() {
    }

    public Materia(String materiaNombre, String maestroNombre, String horario, String aulaNombre, double calificacion) {
        this.materiaNombre = materiaNombre;
        this.maestroNombre = maestroNombre;
        this.horario = horario;
        this.aulaNombre = aulaNombre;
        this.calificacion = calificacion;
    }

    public String getMateriaNombre() {return materiaNombre;}

    public String getMaestroNombre() {return maestroNombre;}

    public String getHorario() {return horario;}

    public String getAulaNombre() {return aulaNombre;}

    public double getCalificacion() {return calificacion;}
}

