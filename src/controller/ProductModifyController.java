package controller;

import static controller.ProductAddController.sumPartsPrice;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Errors;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class for the ModifyProductScreen.FXML document. This class has
 * methods for editing already created products as well as attaching any number 
 * of parts to those products then saving to the inventory system. 
 * The functional methods are called by the various button elements and on 
 * various textual elements in the class.
 *
 * @author sjpaige
 */
public class ProductModifyController implements Initializable{
    Parent scene;
    Stage stage;
    
    //Below all elements of the FXML document
    private ObservableList<Part> partsBeingAssociated = FXCollections.observableArrayList();;
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
    private Button searchButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField searchFieldTxt;

    @FXML
    private TableView<Part> partsAvailableTableView;

    @FXML
    private TableColumn<Part, Integer> availableIDCol;

    @FXML
    private TableColumn<Part, String> availableNameCol;

    @FXML
    private TableColumn<Part, Integer> availableInvCol;

    @FXML
    private TableColumn<Part, Double> availablePriceCol;

    @FXML
    private Button partAddButton;

    @FXML
    private TableView<Part> partsAddedTableView;

    @FXML
    private TableColumn<Part, Integer> addedIDCol;

    @FXML
    private TableColumn<Part, String> addedNameCol;

    @FXML
    private TableColumn<Part, Integer> addedStockCol;
    
    @FXML
    private TableColumn<Part, Double> addedPriceCol;


    //On button adds a part to the product
    @FXML
    void partAddAction(ActionEvent event) throws IOException {
        addPartToProduct();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populates the available parts to be added
        MainScreenController.populateTableParts(Inventory.getAllParts(), partsAvailableTableView, availableIDCol, availableNameCol,availableInvCol, availablePriceCol);
    }    

    //On button press search using text input
    @FXML
    private void searchButton(ActionEvent event) {
        //result holds the parts 
        ObservableList<Part> result = FXCollections.observableArrayList();
        //attempt to look using an int id
        try {
            int id = (Integer.parseInt(searchFieldTxt.getText()));
            result.add(Inventory.lookUpPart(id));
        } catch(Exception e){ //Catch the error if its not an int
            result= Inventory.lookUpPart(searchFieldTxt.getText());
        }
        //populate the table with the results
        MainScreenController.populateTableParts(result,partsAvailableTableView, availableIDCol, availableNameCol,availableInvCol, availablePriceCol);
    
    }

    //On button press saves the prodcut to the inventory
    @FXML
    private void saveAction(ActionEvent event) throws IOException, Exception {
         save(event, name,price, stock, max, min);
    }

    //Delete a part from the list of parts that are being added to the product
    @FXML
    private void deleteAction(ActionEvent event) {
        
        //Get the delete confirmation
        Optional<ButtonType> deletionOptional = Errors.warningPopUp("Confirm Deletion","You wish to delete selected Part?","Press Ok to delete or CANCEL to return.");
        if(deletionOptional.get() == ButtonType.OK){ //If OK procede with deletion
            Part partBeingRemoved = partsAddedTableView.getSelectionModel().getSelectedItem();
            partsBeingAssociated.remove(partBeingRemoved);
            partsAddedTableView.refresh();
        } else if(deletionOptional.get() == ButtonType.CANCEL){ //Otherwise cancel deletion
            partsAddedTableView.refresh();
        }
    }


    //On button press ancel adding a new product
    @FXML
    private void cancelAction(ActionEvent event) throws IOException {
        
        //Product cancellation warning 
        Optional<ButtonType> continueCancel = Errors.warningPopUp("Confirm Cancel","Stop adding this Product?","Press Ok for Main Menu or CANCEL to continue adding this Product.");
        if(continueCancel.get() == ButtonType.OK){ //Confirm cancellation
            backToMainMenu(event); //Return to the MainMenuScreen.FXML
        }    
    }

   /**
     * A method specifically to add a part from the available parts to the parts 
     * that have already been added.
     */
    private void addPartToProduct(){
        //Attempt to select a part
        try{
            
            Part partBeingAdded = partsAvailableTableView.getSelectionModel().getSelectedItem();
            if(partBeingAdded == null){throw new NullPointerException("No Part Selected.");} //If none slected errors out
            partsBeingAssociated.add(partBeingAdded);
            MainScreenController.populateTableParts(partsBeingAssociated, partsAddedTableView, addedIDCol, addedNameCol,addedStockCol, addedPriceCol);
            partsAddedTableView.refresh();
        }catch(NullPointerException e){
            //Error Alert if no part was selected
            Errors.errorPopUp("SELECTION_ERROR", "NullPointerException: ", e.getMessage()); 
        }
    }
    
    /**
     * A getter for the parts being associated with the product
     * @return a ObservableList of parts
     */
    public ObservableList<Part> getPartsBeingAssocated(){
        return partsBeingAssociated;
    }

    /**
     * A setter for the parts being associated with the product
     * @param fromProduct an ObservableList of the parts 
     */
    public void setPartsBeingAssocated(ObservableList<Part> fromProduct){
        partsBeingAssociated = fromProduct;
    }
    
    /**
     * Method used by the MainScreenController to send a product to the 
     * ModifyProductController
     * @param product
     */
    public void sendProduct(Product product){
        Product workingProduct = product;
        
        //Set the TextFields with the values from the product
        ID.setText(String.valueOf(product.getId()));
        name.setText(String.valueOf(product.getName()));
        stock.setText(String.valueOf(product.getStock()));
        price.setText(String.valueOf(product.getPrice()));
        max.setText(String.valueOf(product.getMax()));
        min.setText(String.valueOf(product.getMin()));
        ObservableList<Part> selectedProductParts = product.getAllAssociatedParts();
        MainScreenController.populateTableParts(selectedProductParts,partsAddedTableView, addedIDCol, addedNameCol,addedStockCol, addedPriceCol);
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
        TextField minField) throws IOException, Exception{
        

            
            //Initialized the fields with values
            String name = "";
            double price = 0.00;
            int inventory = 0;
            int max = 0;
            int min = 0;
            Product productBeingSaved = null;
            
            //Attempt to obtain values from the TextFields
            try{
                
                //If the name is empty it throws an exception
                name = nameField.getText();
                if(name.equals("")){throw new Errors.NameEmptyException();}

                //If the price fields is left empty it gracefully calculates 
                //the price using the part cost sum method
                if(priceField.getText().isEmpty()){
                    price = sumPartsPrice(partsBeingAssociated);;
                }else{price = Double.parseDouble(priceField.getText());}
                
                //If no amount in stock is given it defaults to 0
                if(stockField.getText().isEmpty()){
                    inventory = 0;
                } else{inventory = Integer.parseInt(stockField.getText());}
                
                //Max defaults 0 if none is given
                if(maxField.getText().isEmpty()){
                    max = 0;
                }else{max = Integer.parseInt(maxField.getText());}  
                
                //Min defaults 0 if none is given
                if(minField.getText().isEmpty()){
                    min = 0;
                }else{min = Integer.parseInt(minField.getText());}       

                //If min is bigger than max or max smaller than min throws an exception
                if(min>max){
                    throw new Errors.MinMaxException();
                }
                if(inventory < min){ //If there's less in stock than the min it throws exception
                    throw new Errors.InventoryMinException();
                }else if(inventory > max){//If there's  more in stock than max it throws exception
                    throw new Errors.InventoryMaxException();
                }
                //Holds and calculates the sum of the parts cost
                double costForParts = sumPartsPrice(partsBeingAssociated);
                if(price < costForParts){ //checks to make sure price is greater than parts cost
                    throw new Errors.PriceException();
                }
                
                //Throws exception if there are no parts attached to the product
                if(partsBeingAssociated.isEmpty()){
                    throw new Errors.MissingPartsException();
                }
                int id = Integer.parseInt(ID.getText()); //New ID
  
                
                productBeingSaved = (new Product(id, name, price, inventory, min, max));
                
                //Loads the parts to the Product
                for(Part part : partsBeingAssociated){
                    productBeingSaved.addAssociatedPart(part);
                }

                //Adds product to the inventory
                Inventory.updateProduct(id,productBeingSaved);
                
                //Loads the MainMenuScreen.FXML
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenuScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            //Catch all the exceptions thrown using the bounds
            catch(Errors.InventoryMinException e){
                 Errors.errorPopUp("INVENTORY_ERROR","InventoryMinException: ",e.getMessage());
            }
            catch(Errors.InventoryMaxException e){
                 Errors.errorPopUp("INVENTORY_ERROR","InventoryMaxException: ",e.getMessage());
            }
            catch(Errors.MinMaxException e){
                Errors.errorPopUp("MIN_MAX_ERROR","MinMaxException: ",e.getMessage());
            }
            catch(Errors.PriceException e){
                 Errors.errorPopUp("PRICE_ERROR","PriceException: ",e.getMessage());
            }
            catch(Errors.MissingPartsException e){
                Errors.errorPopUp("MISSING_PART_ERROR","MissingPartsException: ",e.getMessage());
            }
            catch(Errors.NameEmptyException e){
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


}
