
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Errors;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class for the MainMenuScreen.FXML document. This class has
 * methods for most of the functionality for that screen. The functional methods
 * are then called by the various button elements and on various textual elements.
 *
 * @author sjpaige
 */
public class MainScreenController implements Initializable {
    //Used to change elements and screens within the program
    Parent scene;
    Stage stage;

    //Below all elements of the FXML document
    @FXML
    private Button searchPart;
    @FXML
    private TextField searchPartText;
    @FXML
    private TableView<Part> tableViewParts;
    @FXML
    private TableColumn<Part, Integer> IDPart;
    @FXML
    private TableColumn<Part, String> namePart;
    @FXML
    private TableColumn<Part, Integer> invLevelPart;
    @FXML
    private TableColumn<Part, Double> costPerPart;
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button searchProduct;
    @FXML
    private TextField searchProductText;
    @FXML
    private TableView<Product> tableViewProducts;
    @FXML
    private TableColumn<Product, Integer> IDProducts;
    @FXML
    private TableColumn<Product, String> nameProducts;
    @FXML
    private TableColumn<Product, Integer> invLevelProducts;
    @FXML
    private TableColumn<Product, Double> cosPerProducts;
    @FXML
    private Button addProductsButton;
    @FXML
    private Button modifyProductsButton;
    @FXML
    private Button deleteProductsButton;
    private Product productBeingModified;
    @FXML
    private Button exitButton;
    
    

    /**
     * Initializes the controller class.
     * @param url an address of a file
     * @param rb the bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       populateTableParts(Inventory.getAllParts(),tableViewParts,IDPart,namePart,invLevelPart,costPerPart);
       populateTableProducts(Inventory.getAllProducts(),tableViewProducts,IDProducts,nameProducts,invLevelProducts,cosPerProducts);
    }    

    //The action event trigger methods from FXML documt
    //On button press search using text input
    @FXML
    private void searchPartAction(ActionEvent event) {
        //result holds the parts 
        ObservableList<Part> result = FXCollections.observableArrayList();
        
        //attempt to look using an int id
        try {
            int id = (Integer.parseInt(searchPartText.getText()));
            result.add(Inventory.lookUpPart(id));
        }  
        catch(Exception e){ //Catch the error if its not an int
            //Find using a string instead
            result= Inventory.lookUpPart(searchPartText.getText());
        }
        //refresh the parts table
        populateTableParts(result,tableViewParts, IDPart, namePart,invLevelPart, costPerPart);
    }

    //On button press change to the AddPartScreen
    @FXML
    private void addPartAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    //On button press invoke the modify method
    @FXML
    private void modifyPartAction(ActionEvent event) throws IOException {
        modify(event, tableViewParts);
    }

    //On button press search using text input
    @FXML
    private void searchProductAction(ActionEvent event) {
        //result holds the products returned
        ObservableList<Product> result = FXCollections.observableArrayList();
        try { //attempts to search using an int id
            int id = (Integer.parseInt(searchProductText.getText()));
            result.add(Inventory.lookUpProduct(id));
        } catch(Exception e){ //defauls to textual search
            result= Inventory.lookUpProduct(searchProductText.getText());
        }
        //repopulate the table with the search results
        populateTableProducts(result,tableViewProducts, IDProducts, nameProducts,invLevelProducts, cosPerProducts);
    }

    //On button press changes to the AddProductScreen
    @FXML
    private void addProductsAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //On button press invokes methods to modify the products
    @FXML
    private void modifyProductsAction(ActionEvent event) throws IOException {
        modifyProduct(event,tableViewProducts);
        setProductBeingModified(tableViewProducts.getSelectionModel().getSelectedItem());
       
    }  
    
    //On button press deletes a selected part
    @FXML
    private void deletePartAction(ActionEvent event) {
        //Sets up a Optional to trigger the responses from the Alert.CONFIRMATION
        Optional<ButtonType> deletionOptional = Errors.warningPopUp("Confirm Deletion","You wish to delete selected Part?","Press Ok to delete or CANCEL to return.");
        
        if(deletionOptional.get() == ButtonType.OK){ //Click the OK button to confrim deletion
            Inventory.deletePart(tableViewParts.getSelectionModel().getSelectedItem());
            tableViewParts.refresh(); //refresh the table 
        } else if(deletionOptional.get() == ButtonType.CANCEL){//Cancel opts not to delete
            tableViewParts.refresh(); //refresh the table 
        }
    } 

    //On button press deletes a selected product
    @FXML
    private void deleteProductsAction(ActionEvent event) {
        //Sets up a Optional to trigger the responses from the Alert.CONFIRMATION
        Optional<ButtonType> deletionOptional = Errors.warningPopUp("Confirm Deletion","You wish to delete selected Product?","Press Ok to delete or CANCEL to return.");
        if(deletionOptional.get() == ButtonType.OK){ //Click the OK button to confrim deletion
            Inventory.deleteProduct(tableViewProducts.getSelectionModel().getSelectedItem());
            tableViewProducts.refresh(); //refresh the table 
        } else if(deletionOptional.get() == ButtonType.CANCEL){ //Cancel opts not to delete
            tableViewProducts.refresh(); //refresh the table 
        }
        tableViewParts.refresh(); //refresh the table 
    }
   
    //On button press exits the inventory system
    @FXML
    private void exitAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * A getter method for the productBeingModified field.
     * @return a product
     */
    public Product getProductBeingModified() {
        return productBeingModified;
    }
    
//    /**
//     *
//     * @return
//     */
//    public TableView getProductsTable(){
//        return tableViewProducts;
//    }

    /**
     * A setter method for the productBeingModified field.
     * @param productBeingModified takes a product
     */
    public void setProductBeingModified(Product productBeingModified) {
        this.productBeingModified = productBeingModified;
    }    
    
    /**
     * This method populates the parts TableView on the MainMenuScreen
     * @param inv a ObservableList of parts 
     * @param table a TableView to show the parts
     * @param id a TableColumn in the TableView that represents the part's id
     * @param name a TableColumn in the TableView that represents the part's name
     * @param stock a TableColumn in the TableView that represents the part's stock
     * @param price a TableColumn in the TableView that represents the part's price
     */
    public static void populateTableParts(
            ObservableList<Part> inv, 
            TableView<Part> table, 
            TableColumn<Part, Integer> id, 
            TableColumn<Part, String> name, 
            TableColumn<Part, Integer> stock,
            TableColumn<Part, Double> price){
            
            table.setItems(inv); 
            
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
        
    /**
     * This method populates the products TableView on the MainMenuScreen
     * @param inv a ObservableList of products 
     * @param table a TableView to show the products
     * @param id a TableColumn in the TableView that represents the product's id
     * @param name a TableColumn in the TableView that represents the product's name
     * @param stock a TableColumn in the TableView that represents the product's stock
     * @param price a TableColumn in the TableView that represents the product's price
     */
    public static void populateTableProducts(
            ObservableList<Product> inv, 
            TableView<Product> table, 
            TableColumn<Product, Integer> id, 
            TableColumn<Product, String> name, 
            TableColumn<Product, Integer> stock,
            TableColumn<Product, Double> price){
        
            table.setItems(inv);
            
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
         
    }        

    
    /** 
     * The modify method that sends the values from the MainMenuScreen to the 
     * ModifyPartScreen
     * @param event an ActionEvent that triggers the screen change
     * @param table the table from where the selection is made
     */
    private void modify(ActionEvent event, TableView<Part> table) throws IOException{
        
        //Select a part from the table
        Part selectedPart = (Part) table.getSelectionModel().getSelectedItem();
        
        //Initialize a new loader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartScreen.fxml"));
        loader.load();
        
        //Initialize a the controller
        ModifyPartController MPController = loader.getController();
        MPController.sendPart(selectedPart); //sends the part to the ModifyPartScreen

        //Decides if it is loading an InHouse or an Outsourced part
        if(selectedPart instanceof InHouse){
           MPController.inhousePartLoad();
            
        } else {MPController.outsourcedPartLoad();}
        
        //Change the scene
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent theScene = loader.getRoot();
        stage.setScene(new Scene(theScene));
        stage.show();
    }
    

    /** 
     * The modify method that sends the values from the MainMenuScreen to the 
     * ModifyProductcreen
     * @param event an ActionEvent that triggers the screen change
     * @param table the table from where the selection is made
     */    
    private void modifyProduct(ActionEvent event, TableView<Product> table) throws IOException{

        //Select a part from the table        
        Product productType = (Product) table.getSelectionModel().getSelectedItem();
        //Initialize a new loader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductScreen.fxml"));
        loader.load();
        
        //Initialize a the controller
        ProductModifyController PMController = loader.getController();
        
        //sends the product to the ModifyProductScreen
        PMController.sendProduct((Product) table.getSelectionModel().getSelectedItem());
        //sends the parts to the ModifyProductScreen
        PMController.setPartsBeingAssocated(productType.getAllAssociatedParts());
        
        //Change the scene
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent theScene = loader.getRoot();
        stage.setScene(new Scene(theScene));
        stage.show();
    } 
}
