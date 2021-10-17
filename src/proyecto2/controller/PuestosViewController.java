/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import proyecto2.model.Puesto;
import proyecto2.util.Formato;
import proyecto2.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class PuestosViewController implements Initializable {

    private Puesto enEdicion = null;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtSalario;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TableView<Puesto> tbPuestos;
    @FXML
    private TableColumn<Puesto, String> clNombre;
    @FXML
    private TableColumn<Puesto, String> clSalario;
    @FXML
    private TableColumn<Puesto, String> clDescripcion;
    @FXML
    private TableColumn<Puesto, Void> clacciones;
    private final List<Puesto> temporalList = new ArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepararTabla();
        rellenarTabla();
        txtSalario.setTextFormatter(Formato.getInstance().twoDecimalFormat());
    }

    private void prepararTabla() {
        tbPuestos.setPlaceholder(new Label("No hay puesto para mostrar"));
        clNombre.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getNombre()));
        clSalario.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getSalario().toString()));

        clDescripcion.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().
                getDescripcion().substring(0, (x.getValue().getDescripcion().length() < 25)
                        ? x.getValue().getDescripcion().length() : 25)));
        agregarBotonEditar();

    }

    private void agregarBotonEditar() {
        Callback<TableColumn<Puesto, Void>, TableCell<Puesto, Void>> cellFactory = (final TableColumn<Puesto, Void> param) -> {
            final TableCell<Puesto, Void> cell = new TableCell<Puesto, Void>() {

                private final Button btn = new Button("Editar");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        enEdicion = getTableView().getItems().get(getIndex());
                        bindearCampos();
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

        clacciones.setCellFactory(cellFactory);
    }

    @FXML
    private void Buscar(ActionEvent event) {
        @SuppressWarnings("All")
        List<Puesto> resultados = new ArrayList();

        resultados = temporalList.stream().filter(
                itm -> (itm.getNombre().toUpperCase().contains(txtNombre.getText().toUpperCase()))
                && (itm.getSalario().toString().contains(txtSalario.getText()))
                && (itm.getDescripcion().toUpperCase().contains(txtDescripcion.getText().toUpperCase())))
                .collect(Collectors.toList());

        tbPuestos.getItems().clear();
        tbPuestos.getItems().addAll(resultados);
    }

    private void rellenarTabla() {

        Puesto p1 = new Puesto("Pediatria", 2000000f, "Trabajan con niños");
        Puesto p2 = new Puesto("Cardiología", 3000000f, "Trabajan con corazones");
        Puesto p3 = new Puesto("Oncología", 4000000f, "Trabajan con cancer");
        Puesto p4 = new Puesto("Otorrinonaringología", 5000000f, "Trabajan con caras");
        Puesto p5 = new Puesto("Psicología", 6000000f, "Trabajan con mentes");

        temporalList.addAll(Arrays.asList(p1, p2, p3, p4, p5));

    }

    @FXML
    private void gaurdarCambios(ActionEvent event) {
        if (esValidoParaGuardar()) {
            if (enEdicion != null) {
                enEdicion.cambiarDatos(txtNombre.getText().trim(),
                        Float.valueOf(txtSalario.getText()), txtDescripcion.getText().trim());
                limpiarCampos(null);
                new Mensaje().show(Alert.AlertType.INFORMATION, "Acción completada", "Has actualizado un puesto");
            } else {
                Puesto nuevo = new Puesto(txtNombre.getText().trim(),
                        Float.valueOf(txtSalario.getText()), txtDescripcion.getText().trim());

                new Mensaje().show(Alert.AlertType.INFORMATION, "Acción completada", "Has registrado un puesto");
                temporalList.add(nuevo);
                limpiarCampos(null);
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Atención",
                    this.tbPuestos.getScene().getWindow(), "Parece que hay algunos campos requeridos que continuan vacíos.");
        }
    }

    @FXML
    private void limpiarCampos(ActionEvent event) {
        txtDescripcion.setText("");
        txtNombre.setText("");
        txtSalario.setText("");
        enEdicion = null;
    }

    private boolean esValidoParaGuardar() {
        return !txtDescripcion.getText().trim().isEmpty()
                && !txtNombre.getText().trim().isEmpty()
                && !txtSalario.getText().trim().isEmpty();
    }
    
    private void bindearCampos(){
        txtDescripcion.setText(enEdicion.getDescripcion());
        txtNombre.setText(enEdicion.getNombre());
        txtSalario.setText(enEdicion.getSalario().toString());
    }

}
