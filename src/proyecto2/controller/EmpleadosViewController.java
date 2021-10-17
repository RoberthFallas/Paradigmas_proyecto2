/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import proyecto2.model.Empleado;
import proyecto2.model.Puesto;
import proyecto2.util.AppContext;
import proyecto2.util.Formato;
import proyecto2.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class EmpleadosViewController implements Initializable {

    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPApellido;
    @FXML
    private TextField txtSApellido;
    @FXML
    private DatePicker dtpFechaNacimiento;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Button btnGuardar;

    private final ArrayList<Empleado> empleados = new ArrayList<>();
    @FXML
    private TableView<Empleado> tblEmpleados;
    public ObservableList<Empleado> Empleadostabla;
    @FXML
    private TableColumn<Empleado, String> tbCedula;
    @FXML
    private TableColumn<Empleado, String> tblNombre;
    @FXML
    private TableColumn<Empleado, String> tblPapellido;
    @FXML
    private TableColumn<Empleado, String> tblTelefono;
    @FXML
    private TableColumn<Empleado, String> tblCorreo;
    @FXML
    private TableColumn<Empleado, String> tblSapellido;
    @FXML
    private TableColumn<Empleado, Void> tblEditar;
    @FXML
    private TableColumn<Empleado, Void> tblEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnBuscar;
    int posicionEditar = -1;
    @FXML
    private TableColumn<Empleado, String> tblFechaNacimiento;
    @FXML
    private TextField txtHorasLaboradas;
    @FXML
    private TableColumn<Empleado, String> tblPuesto;
    @FXML
    private TableColumn<Empleado, String> tblHorasLaborales;
    @FXML
    private ChoiceBox<Puesto> ckPuestos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(12));
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(12));
        txtPApellido.setTextFormatter(Formato.getInstance().letrasFormat(12));
        txtSApellido.setTextFormatter(Formato.getInstance().letrasFormat(14));
        txtTelefono.setTextFormatter(Formato.getInstance().integerFormat());
        txtHorasLaboradas.setTextFormatter(Formato.getInstance().integerFormat());
        cargarPropiedadesTable();
        cargarPuestos();
    }

    @FXML
    private void GuardarEmpleado(ActionEvent event) {

        if (formularioCompleto()) {
            Empleado nuevoEmpledo = new Empleado(txtCedula.getText(), txtNombre.getText(), txtPApellido.getText(), txtSApellido.getText(), dtpFechaNacimiento.getValue(), ckPuestos.getValue(), txtTelefono.getText(), txtCorreo.getText(), Integer.parseInt(txtHorasLaboradas.getText()));
            if (!btnCancelar.isVisible()) {
                if (!existeEmpleado(nuevoEmpledo)) {
                    empleados.add(nuevoEmpledo);
                    AppContext.getInstance().set("listEmpleados", empleados);
                    tblEmpleados.getItems().addAll(empleados);
                    limpiar();
                }else{
                    new Mensaje().show(Alert.AlertType.WARNING, "Atención", "Este empleado ya existe");
                }
            } else {
                editarEmpleado(nuevoEmpledo);
                limpiar();
            }
            tblEmpleados.getItems().clear();
            tblEmpleados.getItems().addAll(empleados);
        } else {
            new Mensaje().show(Alert.AlertType.WARNING, "Atención", "Algunos campos requeridos están vacíos");
        }
    }

    private void cargarPropiedadesTable() {
        tblEmpleados.setPlaceholder(new Label("Sin Empleados"));
        tbCedula.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getCedula()));
        tblNombre.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getNombre()));
        tblPapellido.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getpApellido()));
        tblSapellido.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getsApellido()));
        tblTelefono.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getTelefono()));
        tblCorreo.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getEmail()));
        tblFechaNacimiento.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getFechaNacimiento().toString()));
        tblHorasLaborales.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getHorasLaborales())));
        tblPuesto.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPuesto().getNombre()));
        agregarBotonEditar();
        agregarBotonElimiar();
    }

    private void limpiar() {
        txtCedula.setText("");
        txtCorreo.setText("");
        txtNombre.setText("");
        txtPApellido.setText("");
        txtSApellido.setText("");
        txtTelefono.setText("");
        txtHorasLaboradas.setText("");
        ckPuestos.setValue(null);
        dtpFechaNacimiento.setValue(null);

    }

    private void agregarBotonElimiar() {
        Callback<TableColumn<Empleado, Void>, TableCell<Empleado, Void>> cellFactory = (final TableColumn<Empleado, Void> param) -> {
            final TableCell<Empleado, Void> cell = new TableCell<Empleado, Void>() {
                private final Button btn = new Button("Eliminar");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        eliminarEmpleado(getTableView().getItems().get(getIndex()));
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };
        tblEliminar.setCellFactory(cellFactory);
    }

    private void agregarBotonEditar() {
        Callback<TableColumn<Empleado, Void>, TableCell<Empleado, Void>> cellFactory = (final TableColumn<Empleado, Void> param) -> {
            final TableCell<Empleado, Void> cell = new TableCell<Empleado, Void>() {
                private final Button btn = new Button("Editar");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        cargarDatosClienteSeleccionado(getTableView().getItems().get(getIndex()));
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };

        tblEditar.setCellFactory(cellFactory);
    }

    void eliminarEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        tblEmpleados.getItems().remove(empleado);
        AppContext.getInstance().set("listEmpleados", empleados);
    }

    void cargarDatosClienteSeleccionado(Empleado empleado) {
        for (int x = 0; x < empleados.size(); x++) {
            if (empleados.get(x) == empleado) {
                posicionEditar = x;
            }
        }
        txtCedula.setText(empleado.getCedula());
        txtCorreo.setText(empleado.getEmail());
        txtNombre.setText(empleado.getNombre());
        txtPApellido.setText(empleado.getpApellido());
        txtSApellido.setText(empleado.getsApellido());
        txtTelefono.setText(empleado.getTelefono());
        txtHorasLaboradas.setText(empleado.getHorasLaborales().toString());
        dtpFechaNacimiento.setValue(empleado.getFechaNacimiento());
        ckPuestos.setValue(empleado.getPuesto());
        tblEmpleados.setDisable(true);
        btnCancelar.setVisible(true);
        btnBuscar.setVisible(false);
    }

    @FXML
    private void CancelarEdicion(ActionEvent event) {
        btnBuscar.setVisible(true);
        btnCancelar.setVisible(false);
        tblEmpleados.setDisable(false);

        limpiar();
    }

    @FXML
    private void buscarEmpleado(ActionEvent event) {
        String e = txtHorasLaboradas.getText();
        String e1 = txtCedula.getText();
        String e2 = txtHorasLaboradas.getText();
        tblEmpleados.getItems().clear();
        List<Empleado> emple = new ArrayList();
        emple = empleados.stream().filter(x ->
                (x.getCedula().toUpperCase().contains(txtCedula.getText().toUpperCase()))
                && (x.getNombre().toUpperCase().contains(txtNombre.getText().toUpperCase()))
                && (x.getpApellido().toUpperCase().contains(txtPApellido.getText().toUpperCase()))
                && (x.getsApellido().toUpperCase().contains(txtSApellido.getText().toUpperCase()))).collect(Collectors.toList());
        emple.forEach(cnsmr -> {
            tblEmpleados.getItems().add(cnsmr);
        });
    }

    private void editarEmpleado(Empleado nuevoEmpleado) {
        btnBuscar.setVisible(true);
        btnCancelar.setVisible(false);
        tblEmpleados.setDisable(false);
        empleados.set(posicionEditar, nuevoEmpleado);
        AppContext.getInstance().set("listEmpleados", empleados);

    }

    private boolean existeEmpleado(Empleado nuevoEmpledo) {
        for (Empleado empleado : empleados) {
            if (empleado.getCedula().equals(nuevoEmpledo.getCedula())) {
                return true;
            }
        }
        return false;
    }

    private boolean formularioCompleto() {
        if (txtCedula.getText().length() != 0 && txtNombre.getText().length() != 0
                && txtPApellido.getText().length() != 0
                && txtSApellido.getText().length() != 0
                && txtTelefono.getText().length() != 0
                && txtCorreo.getText().length() != 0
                && txtHorasLaboradas.getText().length() != 0
                && dtpFechaNacimiento.getValue() != null && ckPuestos.getValue() != null) {
            return true;
        }
        return false;
    }

    private void cargarPuestos() {
        ObservableList<Puesto> listaLocal = (ObservableList<Puesto>) AppContext.getInstance().get("puestos");
        ckPuestos.setItems(listaLocal);
    }

}
