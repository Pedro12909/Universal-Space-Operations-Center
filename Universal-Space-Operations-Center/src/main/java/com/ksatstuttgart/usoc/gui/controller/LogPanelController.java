/*
 * The MIT License
 *
 * Copyright 2017 KSat Stuttgart e.V..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ksatstuttgart.usoc.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

// Imports for serialLog or iridiumLog
import com.ksatstuttgart.usoc.controller.MainController;
import com.ksatstuttgart.usoc.controller.MessageController;
import com.ksatstuttgart.usoc.data.DataSource;
import com.ksatstuttgart.usoc.data.ErrorEvent;
import com.ksatstuttgart.usoc.data.USOCEvent;
import javafx.scene.control.TextArea;

// Specific imports for serialLog
import com.ksatstuttgart.usoc.controller.communication.SerialComm;
import java.util.ArrayList;
import com.ksatstuttgart.usoc.data.SerialEvent;
import javafx.scene.control.ComboBox;

/** 
 * <h1>LogPanelController</h1>
 * This class ensures the functionality of the LogPanel.
 * <p>
 * Apart from pre-programmed methods for the SerialLog and IridiumLog,
 * a dummy method is written for each button of the individually designed
 * tabs. These methods can be supplemented manually. Those method contents are
 * recognized and will not be lost if the button label has been changed and the
 * corresponding FXML structure and controller regenerated.
 * The LogPanelController is generated automatically.
 * 
 * @author Victor Hertel
 * @version 1.0
*/
public class LogPanelController extends DataController implements Initializable { 

    @FXML private ComboBox comboBox1; 
    @FXML private ComboBox comboBox2; 
    @FXML private ComboBox comboBox3; 
    @FXML private TextArea serialTextArea; 

    @FXML 
    public void setData() { 
        comboBox1.getItems().setAll(SerialComm.getInstance().getAvailableBaudrates()); 
        comboBox3.getItems().setAll(SerialComm.getInstance().getAvailableCommands()); 
    } 

    @FXML 
    public void updatePortList(ArrayList<String> portList) { 
        if (comboBox2 != null) { 
            comboBox2.getItems().setAll(portList); 
        } 
    } 

    @FXML 
    private void serialConnect(ActionEvent event) { 
        System.out.println("Connect button in serial log has been pressed!");
        String port = comboBox2.getSelectionModel().getSelectedItem().toString();
        int baudrate = Integer.parseInt(comboBox1.getSelectionModel().getSelectedItem().toString());
        SerialComm.getInstance().start(port, baudrate); 
    } 

    @FXML 
    private void serialSendCommand(ActionEvent event) { 
        System.out.println("Send Command button in serial log has been pressed!"); 
        String output = comboBox3.getSelectionModel().getSelectedItem().toString(); 
        SerialComm.getInstance().send(output);
    } 

    @Override
    public void updateData(MessageController msgController, USOCEvent ue) {
        if (ue instanceof SerialEvent) {
            serialTextArea.setText(((SerialEvent)ue).getMsg());
        } else if (ue instanceof ErrorEvent) {
            if (DataSource.SERIAL == ue.getDataSource()) {
                serialTextArea.setText(((ErrorEvent) ue).getErrorMessage());
            }
        } else {
            serialTextArea.setText(msgController.getData().toString());
        }
    }

    @FXML 
    private void button11(ActionEvent event) { 
        // Automatically generated method button11() 
        System.out.println("Button11 was pressed!"); 
    } 

    @FXML 
    private void button12(ActionEvent event) { 
        // Automatically generated method button12() 
        System.out.println("Button12 was pressed!"); 
    } 

    @Override 
    public void initialize(URL url, ResourceBundle rb) { 
        // TODO
        MainController.startPortThread(this);
        MainController.getInstance().addDataUpdateListener(new UpdateListener());
        setData();
    } 
}