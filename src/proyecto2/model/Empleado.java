package proyecto2.model;


import java.time.LocalDate;

public class Empleado {

    private String cedula;

    private String nombre;

    private String pApellido;

    private String sApellido;

    private LocalDate fechaNacimiento;

    private Puesto puesto;

    private String telefono;
    private String estadoPago;
    private String email;
    private Integer horasLaborales;

    public Empleado(String cedula, String nombre, String pApellido, String sApellido, LocalDate fechaNacimiento, Puesto puesto, String telefono, String email, int horasLaborales) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.pApellido = pApellido;
        this.sApellido = sApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.puesto = puesto;
        this.telefono = telefono;
        this.email = email;
        this.horasLaborales = horasLaborales;
        this.estadoPago = "Pendiente";
    }

    
    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getpApellido() {
        return pApellido;
    }

    public void setpApellido(String pApellido) {
        this.pApellido = pApellido;
    }

    public String getsApellido() {
        return sApellido;
    }

    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHorasLaborales() {
        return horasLaborales;
    }

    public void setHorasLaborales(Integer horasLaborales) {
        this.horasLaborales = horasLaborales;
    }

  
}
