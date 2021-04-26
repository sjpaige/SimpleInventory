
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Errors;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;


/**
 * FXML Controller class for the ModifyPartScreen.FXML. This class loads data
 * from the MainMenuScreen which can then be edited and saved back to the 
 * inventory system.
 *
 * @author admin
 */
public class ModifyPartController implements Initializable {

    Parent scene;
    Stage stage;
    
    //Below all elements of the FXML document
    @FXML
    private ToggleGroup productOriginGrp;
    @FXML
    private RadioButton inhouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private Label IDorCompanyLbl;
    @FXML
    private TextField addMachineIDorCompanyName;
    @FXML
    private TextField ID;
    @FXML
    private TextField name;
    @FXML
    private TextField stock;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private void saveAction(ActionEvent event) throws IOException {
        save(event, name,price, stock, max, min); //Button press invokes save()
    }

    @FXML
    private void cancelAction(ActionEvent event) throws IOException {
        //Triggers an AlertType.CONFIRMATION when you try to cancel the edit
        Optional<ButtonType> continueCancel = Errors.warningPopUp("Confirm Cancel","Stop modifying this Part?","Press Ok for Main Menu or CANCEL to continue modifying this part.");
        if(continueCancel.get() == ButtonType.OK){
            backToMainMenu(event); 
        }    
    }

    @FXML
    private void inhouseRadioAction(ActionEvent event) throws IOException {
        inhousePartLoad(); //When the radio is changed to InHouse it loads the InHouse data
    }

    @FXML
    private void outsourcedRadioAction(ActionEvent event) throws IOException {
        outsourcedPartLoad(); //When radio is triggered loads Outsourced data
    }

    /**
     * This method saves all the data back to the inventory system while
     * handling input errors of many types.
     * @param event an ActionEvent that triggers the method
     * @param nameField a TextField that holds the name
     * @param priceField a TextField that holds the price
     * @param stockField a TextField that holds the stock
     * @param maxField a TextField that holds the max
     * @param minField a TextField that holds the min
     * @throws an IOException if the TextFields are incorrect
     * @throws Errors.MinMaxException() 
     * @throws Errors.InventoryMinException()
     * @throws Errors.InventoryMaxException()
     * @throws Errors.NameEmptyException()
     * @throws Errors.NumberFormatException()
     * 
     */
    private void save( ActionEvent event, 
            TextField nameField, 
            TextField priceField, 
            TextField stockField,
            TextField maxField, 
            TextField minField) throws IOException{
       
        try{
            int id = Integer.parseInt(ID.getText());
       
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int inventory = Integer.parseInt(stockField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());
            
            //Throw exceptions for min max bounds and lack of name
            if(min>max){
                throw new Errors.MinMaxException();
            }
            if(inventory < min){
                throw new Errors.InventoryMinException();
            }else if(inventory > max){
                throw new Errors.InventoryMaxException();
            }
            if(name.isEmpty()){
                throw new Errors.NameEmptyException();
            }

            //Save an InHouse or an Outsourced part
            if(inhouseRadio.selectedProperty().getValue() == true){
                int machineID = Integer.parseInt(addMachineIDorCompanyName.getText());
                Inventory.updatePart(id,new InHouse(id,name,price,inventory,min,max,machineID));

            } else{
                String companyName = addMachineIDorCompanyName.getText();
                Inventory.updatePart(id,new Outsourced(id,name,price,inventory,min,max,companyName));
            }
            
            //Loads back to the MainMenuScreen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenuScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();  
      
        }
        
        //Catch the errors for the save bounds and produce Alerts with proper text
        catch(Errors.InventoryMinException e){
            Errors.errorPopUp("INVENTORY_ERROR","InventoryMinException: ",e.getMessage());
        }
        catch(Errors.InventoryMaxException e){
            Errors.errorPopUp("INVENTORY_ERROR","InventoryMaxException: ",e.getMessage());
        }
        catch(Errors.MinMaxException e){
            Errors.errorPopUp("MIN_MAX_ERROR","MinMaxException: ",e.getMessage());
        }
        catch(NumberFormatException e){
            Errors.errorPopUp("MACHINE_ID_ERROR","NumberFormatException: ","Machine ID must be an integer value.");
        }catch (Errors.NameEmptyException e) {
            Errors.errorPopUp("MISSING_NAME","NameEmptyException: ",e.getMessage());
        }

    }

    //Loads back to the MainMenuScreen
    private void backToMainMenu(Event e) throws IOException{
        stage = (Stage)((Button)e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenuScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method used by the MainScreenController to send a part to the 
     * ModifyPartController
     * @param part
     */
    public void sendPart(Part part){
        
        //Set the TextFields with the values from the part
        ID.setText(String.valueOf(part.getId()));
        name.setText(String.valueOf(part.getName()));
        stock.setText(String.valueOf(part.getStock()));
        price.setText(String.valueOf(part.getPrice()));
        max.setText(String.valueOf(part.getMax()));
        min.setText(String.valueOf(part.getMin()));

        //Loads the proper radio and label if InHouse or Outsourced
        if(part instanceof InHouse){ 
            addMachineIDorCompanyName.setText(String.valueOf(((InHouse) part).getMachineId()));  
        } else {addMachineIDorCompanyName.setText(String.valueOf(((Outsourced) part).getCompanyName()));}
    }    

    /**
     * Method just to change the text and radio for an Outsourced part
     */
    public void outsourcedPartLoad(){
        IDorCompanyLbl.setText("Company Name");
        addMachineIDorCompanyName.promptTextProperty().set("Company Name");
        productOriginGrp.selectToggle(outsourcedRadio);
    }

    /**
     * Method just to change the text and radio for an InHouse part
     */
    public void inhousePartLoad(){
        IDorCompanyLbl.setText("Machine ID");
        addMachineIDorCompanyName.promptTextProperty().set("Mach ID");
        productOriginGrp.selectToggle(inhouseRadio);
     }
    
}
