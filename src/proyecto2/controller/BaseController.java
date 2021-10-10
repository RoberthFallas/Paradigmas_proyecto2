/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import proyecto2.Proyecto2;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class BaseController implements Initializable {

    @FXML
    private Tab tab_empleados;
    @FXML
    private Tab tab_puestos;
    @FXML
    private Tab tab_planillas;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab_empleados.setContent(this.getTabContent("EmpleadosView"));
        tab_puestos.setContent(this.getTabContent("PuestosView"));
        tab_planillas.setContent(this.getTabContent("PlanillasView"));
    }

    private AnchorPane getTabContent(String name){
        try{
            AnchorPane root = FXMLLoader.load(Proyecto2.class.getResource("view/"+name+".fxml"));
            return root;
        }catch(IOException IO){
            return null;
        }
    }
    
}
