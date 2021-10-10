/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

/**
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
