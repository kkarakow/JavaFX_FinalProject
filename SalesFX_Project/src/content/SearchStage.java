package content;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
Katarzyna Karakow
Student Number: 991627844
FINAL PROJECT
April 17, 2021
*/

public class SearchStage extends Stage {

    private ArrayList<Employee> employeeList = new ArrayList();
    private TextArea txtDisplay = new TextArea();
    private RadioButton radCity = new RadioButton("City");
    private RadioButton radPosition = new RadioButton("Position");
    private Label lblSearch = new Label("Search: ");
    private TextField txtSearch = new TextField();
    private Button btnSearch = new Button("Search");
    private Scene scene = new Scene(setDisplay(), 400, 300);

    // SearchStage constructor
    public SearchStage(ArrayList<Employee> employeeList) {

        this.employeeList = employeeList;
        btnSearch.setOnAction(new SearchEmployee());
        scene.getStylesheets().add("/css/TheStyle.css");
        setScene(scene);
    }

    /*
    Event Handler for btnSearch, allows to search records in employeeList by city
    and position, depens on which RadioButton is checked. Displays only records that matches 
    search requirment typed into text field txtSearch. If any of the records matches
    alert error will appear.
     */
    public class SearchEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            String data = new String();
            boolean match = false;
            if (radCity.isSelected()) {

                String city;
                for (int sub = 0; sub < employeeList.size(); sub++) {
                    city = employeeList.get(sub).getCity();
                    if (city.equalsIgnoreCase(txtSearch.getText())) {
                        match = true;
                    }
                }
                if (match == true) {
                    Iterator<Employee> itEmployee = employeeList.iterator();
                    while (itEmployee.hasNext()) {
                        Employee one = itEmployee.next();
                        if (one.getCity().equalsIgnoreCase(txtSearch.getText())) {
                            data += one.getID() + ", " + one.getName() + ", " + one.getCity()
                                    + ", " + one.getPosition() + "\n";
                        }
                    }
                    txtSearch.clear();
                    txtSearch.requestFocus();
                    txtDisplay.setText(data);
                }
                if (match == false) {
                    Alert dlgError = new Alert(Alert.AlertType.ERROR);
                    dlgError.setContentText("Does not match any record");
                    dlgError.show();
                }
            }
            if (radPosition.isSelected()) {
                String position;

                for (int sub = 0; sub < employeeList.size(); sub++) {
                    position = employeeList.get(sub).getPosition();
                    if (position.equalsIgnoreCase(txtSearch.getText())) {
                        match = true;
                    }
                }
                if (match == true) {
                    Iterator<Employee> itEmployee = employeeList.iterator();
                    while (itEmployee.hasNext()) {
                        Employee one = itEmployee.next();
                        if (one.getPosition().equalsIgnoreCase(txtSearch.getText())) {
                            data += one.getID() + ", " + one.getName() + ", " + one.getCity()
                                    + ", " + one.getPosition() + "\n";
                        }
                    }
                    txtSearch.clear();
                    txtSearch.requestFocus();
                    txtDisplay.setText(data);
                }
                if (match == false) {
                    Alert dlgError = new Alert(Alert.AlertType.ERROR);
                    dlgError.setContentText("Does not match any record");
                    dlgError.show();
                }
            }
        }
    }

    // Method to set BorderPane layout for SearchStage
    private BorderPane setDisplay() {

        BorderPane pane = new BorderPane();
        GridPane tPane = new GridPane();
        GridPane bPane = new GridPane();
        
        tPane.add(radCity, 1, 1);
        tPane.add(radPosition,2,1);
        tPane.setHgap(10);
        tPane.setAlignment(Pos.CENTER);
               
        bPane.add(lblSearch, 1, 1);
        bPane.add(txtSearch, 2, 1);
        bPane.add(btnSearch, 3, 1);
        txtSearch.setPrefSize(200, 40);
        btnSearch.setPrefSize(80, 30);
        bPane.setHgap(10);
        bPane.setAlignment(Pos.CENTER);
        
        radCity.setSelected(true);
        ToggleGroup group = new ToggleGroup();
        radCity.setToggleGroup(group);
        radPosition.setToggleGroup(group);
        
        txtDisplay.setPrefSize(300, 300);
        
        pane.setTop(tPane);
        pane.setCenter(txtDisplay);
        pane.setBottom(bPane);
        
        BorderPane.setMargin(pane, new Insets(10));
        BorderPane.setMargin(bPane, new Insets(10));
        BorderPane.setMargin(tPane, new Insets(10));

        return pane;
    }
}
