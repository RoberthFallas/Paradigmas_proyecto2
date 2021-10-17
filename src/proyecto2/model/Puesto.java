package proyecto2.model;


import java.util.ArrayList;
import java.util.List;

public class Puesto {

    private String nombre;
    private Float salario;
    private List<Empleado> empleados;
    private String descripcion;

    public Puesto(String nombre, Float salario, String descripcion) {
        this.nombre = nombre;
        this.salario = salario;
        this.empleados = new ArrayList();
        this.descripcion = descripcion;
    }
    
    
    public void cambiarDatos(String nombre, Float salario, String descripcion){
        this.nombre = nombre;
        this.salario = salario;
        this.empleados = new ArrayList();
        this.descripcion = descripcion;
    }
    

    public Puesto() {
        this.empleados = new ArrayList();
    }

    public String getNombre() {
        return nombre;
    }

    public Float getSalario() {
        return salario;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void addEmpleado(Empleado empleado){
        this.empleados.add(empleado);
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    

}
