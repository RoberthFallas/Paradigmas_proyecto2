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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import proyecto2.model.Empleado;
import proyecto2.model.Puesto;
import proyecto2.util.AppContext;
import proyecto2.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class PlanillasViewController implements Initializable {

    private Empleado emp = null;
     
    @FXML
    private TableColumn<Empleado, String> tblCedula;
    @FXML
    private TableColumn<Empleado, String> tblNombre;
    @FXML
    private TableColumn<Empleado, String> tblPrimerApellido;
    @FXML
    private TableColumn<Empleado, String> tblSegundoApellido;
    @FXML
    private TableColumn<Empleado, String> tblHorasLaboradas;
    @FXML
    private TableColumn<Empleado, String> tblPuestos;
    @FXML
    private TableColumn<Empleado, String> tblSalarioBruto;
    @FXML
    private TableColumn<Empleado, String> tblCargosLAborales;
    @FXML
    private TableColumn<Empleado, String> tblHorasExtra;
    @FXML
    private TableColumn<Empleado, Void> tblAccion;

    ArrayList<Empleado> listaEmpleados = (ArrayList<Empleado>) AppContext.getInstance().get("listEmpleados");
    @FXML
    private TableView<Empleado> tbPlanilla;
    @FXML
    private Button btnObtenerPlanilla;
    @FXML
    private TableColumn<Empleado, String> tblSalarioNeto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        cargarPropiedadesTable();
    }

   

    private void refrescarTabla() {
        tbPlanilla.getItems().clear();
        for (Empleado listaEmpleado : listaEmpleados) {
            if (listaEmpleado.getEstadoPago() == "Pendiente") {
                tbPlanilla.getItems().add(listaEmpleado);
            }
        }
    }

    private void cargarPropiedadesTable() {
        
        tblCedula.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getCedula()));
        tblNombre.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getNombre()));
        tblPrimerApellido.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getpApellido()));
        tblSegundoApellido.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getsApellido()));
        tblHorasLaboradas.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getHorasLaborales())));
        tblPuestos.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPuesto().getNombre()));
        tblSalarioBruto.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getHorasLaborales() * x.getValue().getPuesto().getSalario())));
        tblCargosLAborales.setCellValueFactory(x->new SimpleStringProperty(String.valueOf(x.getValue().getHorasLaborales() * x.getValue().getPuesto().getSalario() * 0.03)));
        tblHorasExtra.setCellValueFactory(x->new SimpleStringProperty(String.valueOf(x.getValue().getHorasExtra() * x.getValue().getPuesto().getSalario())));
       tblSalarioNeto.setCellValueFactory(x->new SimpleStringProperty(String.valueOf((x.getValue().getHorasLaborales() * x.getValue().getPuesto().getSalario())+ x.getValue().getHorasExtra() * x.getValue().getPuesto().getSalario() - x.getValue().getHorasLaborales() * x.getValue().getPuesto().getSalario() * 0.03 )));
        agregarBotonPago();
    }
    
    private void horasExtra(){
         
        
    }
    private void agregarBotonPago() {
        Callback<TableColumn<Empleado, Void>, TableCell<Empleado, Void>> cellFactory = (final TableColumn<Empleado, Void> param) -> {
            final TableCell<Empleado, Void> cell = new TableCell<Empleado, Void>() {

                private final Button btn = new Button("Pagar");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        pagarEmpleado(getTableView().getItems().get(getIndex())); 
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        btn.setStyle("-fx-background-color:#388E3C; -fx-text-fill: whitesmoke;");
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };

        tblAccion.setCellFactory(cellFactory);
    }
    
    private void pagarEmpleado(Empleado emp){
       float pagoHorasExtra = emp.getHorasExtra()*emp.getPuesto().getSalario();
       float salarioBruto = emp.getHorasLaborales()*emp.getPuesto().getSalario();
       double cargosLaborales = salarioBruto * 0.03;
       double salarioNeto = (pagoHorasExtra+ salarioBruto)-cargosLaborales;
        emp.setEstadoPago("Pagado");
        emp.setHorasLaborales(0);
        new Mensaje().show(Alert.AlertType.INFORMATION, "Pago de empleado", "Se ha cancelado la planilla del empleado "+emp.getNombre()+" "+emp.getpApellido()+" "+emp.getsApellido()+" por un total de: "+salarioNeto);
         refrescarTabla();
    }

    @FXML
    private void ObtenerPlanilla(ActionEvent event) {
         refrescarTabla();
    }
    
    
}
