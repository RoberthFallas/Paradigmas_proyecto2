package proyecto2.model;


import java.util.List;

public class Puesto {

    private String nombre;

    private Float salario;

    private List<Empleado> empleados;

    private String descripcion;

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


}
