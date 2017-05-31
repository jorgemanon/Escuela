package com.jje.programacion.escuela.modelo;

public class Alumno extends Item {
    private String alumnoId, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, direccion, telefono, semestre, carrera, foto;

    public Alumno(String alumnoId, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String direccion, String telefono, String semestre, String carrera, String foto) {
        this.alumnoId = alumnoId;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.semestre = semestre;
        this.carrera = carrera;
        this.foto = foto;
    }

    public String getAlumnoId() {return alumnoId;}

    public String getNombre() {return nombre;}

    public String getApellidoPaterno() {return apellidoPaterno;}

    public String getApellidoMaterno() {return apellidoMaterno;}

    public String getFechaNacimiento() {return fechaNacimiento;}

    public String getDireccion() {return direccion;}

    public String getTelefono() {return telefono;}

    public String getSemestre() {return semestre;}

    public String getCarrera() {return carrera;}

    public String getFoto() {return foto;}

    @Override
    public String toString() {
        return "ID:"+alumnoId+"\nNombre:"+nombre+"\nCarrera:"+carrera+"\nSemestre:"+semestre;
    }
}
