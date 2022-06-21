package salesfx_project;

import content.Employee;
import content.EmployeeFile;
import content.SearchStage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
Katarzyna Karakow
Student Number: 991627844
FINAL PROJECT
April 17, 2021
 */
public class Main extends Application {

    private ArrayList<Employee> employeeList = new ArrayList();
    private int recordPosition = 0;
    private Label lblID = new Label("Employee ID: ");
    private TextField txtID = new TextField();
    private Label lblName = new Label("Employee Name: ");
    private TextField txtName = new TextField();
    private Label lblCity = new Label("Employee City: ");
    private TextField txtCity = new TextField();
    private Label lblPosition = new Label("Employee Position: ");
    private TextField txtPosition = new TextField();
    private Button btnAdd = new Button("Add");
    private Button btnUpdate = new Button("Update");
    private Button btnDelete = new Button("Delete");
    private Button btnSearch = new Button("Search");
    private Button btnFirst = new Button("First");
    private Button btnNext = new Button("Next");
    private Button btnPrevious = new Button("Previous");
    private Button btnLast = new Button("Last");
    private HBox bPane = new HBox(btnFirst, btnNext, btnPrevious, btnLast);

    @Override
    public void start(Stage stage) throws Exception {

        displayEmployee();

        btnFirst.setOnAction(new FirstEmployee());
        btnNext.setOnAction(new NextEmployee());
        btnPrevious.setOnAction(new PreviousEmployee());
        btnLast.setOnAction(new LastEmployee());

        btnAdd.setOnAction(new AddEmployee());
        btnUpdate.setOnAction(new UpdateEmployee());
        btnDelete.setOnAction(new DeleteEmployee());
        btnSearch.setOnAction(new SearchEmployee());

        stage.setOnCloseRequest(new EndProgram());

        Scene scene = new Scene(employeePane(), 625, 215);
        scene.getStylesheets().add("/css/TheStyle.css");
        stage.setScene(scene);
        stage.show();
    }

    /*
    Method to display First element of the ArrayList employeeList from the 
    Employee.dat file, when the program starts up data from the 
    sequentional file Employee.dat is loaded to ArrayList employeeList.
     */
    public void displayEmployee() {

        try {
            EmployeeFile.getEmployee(employeeList);
            Employee one = new Employee();
            one = employeeList.get(0);
            txtID.setText("" + one.getID());
            txtName.setText(one.getName());
            txtCity.setText(one.getCity());
            txtPosition.setText(one.getPosition());
            recordPosition = 1;
        } catch (IOException e) {
            Alert dlgError = new Alert(Alert.AlertType.ERROR);
            dlgError.setHeaderText("Data Not Saved - Program Ended");
            dlgError.setContentText(e.toString());
            dlgError.show();
        }
    }

// Event Handler on btnFirst to display first record in ArraylList employeeList
    public class FirstEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            Employee one = new Employee();
            one = employeeList.get(0);
            txtID.setText("" + one.getID());
            txtName.setText(one.getName());
            txtCity.setText(one.getCity());
            txtPosition.setText(one.getPosition());
            recordPosition = 1;
        }
    }

//Event Handler on btnNext to display next record in ArraylList employeeList
    public class NextEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            if (recordPosition < employeeList.size() && recordPosition > 0) {
                Employee one = new Employee();
                one = employeeList.get(recordPosition);
                txtID.setText("" + one.getID());
                txtName.setText(one.getName());
                txtCity.setText(one.getCity());
                txtPosition.setText(one.getPosition());
                recordPosition++;
            } else if (recordPosition == 0) {
                recordPosition = 1;
                Employee one = new Employee();
                one = employeeList.get(recordPosition);
                txtID.setText("" + one.getID());
                txtName.setText(one.getName());
                txtCity.setText(one.getCity());
                txtPosition.setText(one.getPosition());
                recordPosition++;
            }
        }
    }

// Event Handler on btnPrevious to display previous record in ArraylList employeeList
    public class PreviousEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            if (recordPosition < employeeList.size() && recordPosition > 0) {
                Employee one = new Employee();
                one = employeeList.get(recordPosition - 1);
                txtID.setText("" + one.getID());
                txtName.setText(one.getName());
                txtCity.setText(one.getCity());
                txtPosition.setText(one.getPosition());
                recordPosition--;
            } else if (recordPosition == employeeList.size()) {
                recordPosition = employeeList.size() - 1;
                Employee one = new Employee();
                one = employeeList.get(recordPosition - 1);
                txtID.setText("" + one.getID());
                txtName.setText(one.getName());
                txtCity.setText(one.getCity());
                txtPosition.setText(one.getPosition());
                recordPosition--;
            }
        }
    }

// Event Handler on btnLast to display last record in ArraylList employeeList
    public class LastEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            Employee one = new Employee();
            one = employeeList.get(employeeList.size() - 1);
            txtID.setText("" + one.getID());
            txtName.setText(one.getName());
            txtCity.setText(one.getCity());
            txtPosition.setText(one.getPosition());
            recordPosition = employeeList.size() - 1;
        }
    }

// Event Handler on btnAdd to clear all text fields and allows input of new record    
    public class AddEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            txtID.clear();
            txtName.clear();
            txtCity.clear();
            txtPosition.clear();
            txtID.requestFocus();
            btnAdd.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    /* 
    Event Handler on btnUpdate to save updated or new record to ArrayList employeeList 
    once Update button is clicked, confirmation alert pops up, if user confirms changes
    and press "OK", ID number of new record is checked if its unique or is already assigned to
    a record in employeeList, if its unique, new record is saved to employeeList,
    if its not unique error message will appear; if user press "CANCEL" button orginal
    record will apear. If ID is not a integer NumberFormatException will be catch.
     */
    public class UpdateEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) throws NumberFormatException {

            try {
                Alert dlgConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                dlgConfirm.setContentText("Confirm to update record");
                Optional<ButtonType> result = dlgConfirm.showAndWait();

                if (result.get() == ButtonType.OK) {
                    boolean isEqual = false;
                    int recordID;
                    for (int sub = 0; sub < employeeList.size(); sub++) {
                        recordID = employeeList.get(sub).getID();
                        if (recordID == Integer.parseInt(txtID.getText())) {
                            Alert dlgError = new Alert(Alert.AlertType.ERROR);
                            dlgError.setContentText("Employee ID is not unique" + "\n"
                                    + "Please Enter different ID.");
                            dlgError.show();

                            isEqual = true;
                            break;
                        }
                    }
                    if (isEqual == false) {
                        Employee one = new Employee();
                        int id = Integer.parseInt(txtID.getText());
                        one.setID(id);
                        String name = txtName.getText();
                        one.setName(name);
                        String city = txtCity.getText();
                        one.setCity(city);
                        String position = txtPosition.getText();
                        one.setPosition(position);
                        employeeList.add(one);
                    }
                }
                if (result.get() == ButtonType.CANCEL) {
                    if (recordPosition < employeeList.size() && recordPosition > 0) {
                        Employee one = new Employee();
                        one = employeeList.get(recordPosition);
                        txtID.setText("" + one.getID());
                        txtName.setText(one.getName());
                        txtCity.setText(one.getCity());
                        txtPosition.setText(one.getPosition());
                    }
                    btnAdd.setDisable(false);
                    btnDelete.setDisable(false);
                }
            } catch (NumberFormatException e) {
                Alert dlgError = new Alert(Alert.AlertType.ERROR);
                dlgError.setContentText("Employee ID is not not a number" + "\n"
                        + "Please Enter different ID.");
                dlgError.show();
            }
        }
    }

    /* 
    EventHandler for btnDelete, allows user to delete desired record. If "Delete" button
    is cliced, confirmation alert will appear, asking user to confirm, if user press "OK"
    record will be deleted and information alert will appear, if user clicks "CANCEL"
    record will still appear.
     */
    public class DeleteEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            Alert dlgConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            dlgConfirm.setContentText("Confirm to delete record");
            Optional<ButtonType> result = dlgConfirm.showAndWait();

            if (result.get() == ButtonType.OK) {
                int recordID;
                for (int sub = 0; sub < employeeList.size(); sub++) {
                    recordID = employeeList.get(sub).getID();
                    if (recordID == Integer.parseInt(txtID.getText())) {
                        employeeList.remove(sub);
                        break;
                    }
                }
                Alert dlgInfo = new Alert(Alert.AlertType.INFORMATION);
                dlgInfo.setContentText("Record deleted");
                dlgInfo.show();

                recordPosition--;

                if (recordPosition < employeeList.size() && recordPosition > 0) {
                    Employee one = new Employee();
                    one = employeeList.get(recordPosition);
                    txtID.setText("" + one.getID());
                    txtName.setText(one.getName());
                    txtCity.setText(one.getCity());
                    txtPosition.setText(one.getPosition());
                    recordPosition++;
                }
            }
        }
    }

//  Event Handler for btnSearch, when clicked SearchStage will appear
    public class SearchEmployee implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {

            SearchStage aStage = new SearchStage(employeeList);
            aStage.show();
        }
    }

    /*
    Event Handler for closing application window. When user clicks close button
    all changes are saved to the Employee.dat file
     */
    public class EndProgram implements EventHandler<WindowEvent> {

        @Override
        public void handle(WindowEvent t) {

            try {
                EmployeeFile.setEmployee(employeeList);

                Alert dlgInfo = new Alert(Alert.AlertType.INFORMATION);
                dlgInfo.setContentText("Program Ended - Data Stored");
                dlgInfo.show();

            } catch (IOException e) {

                Alert dlgError = new Alert(Alert.AlertType.ERROR);
                dlgError.setHeaderText("Data Not Saved - Program Ended");
                dlgError.setContentText(e.toString());
                dlgError.show();
            }

        }

    }

// GridPane layout for application primary Stage    
    private GridPane employeePane() {
        GridPane pane = new GridPane();
        pane.add(lblID, 1, 1);
        pane.add(txtID, 2, 1);
        pane.add(btnAdd, 3, 1);
        pane.add(lblName, 1, 2);
        pane.add(txtName, 2, 2);
        pane.add(btnUpdate, 3, 2);
        pane.add(lblCity, 1, 3);
        pane.add(txtCity, 2, 3);
        pane.add(btnDelete, 3, 3);
        pane.add(lblPosition, 1, 4);
        pane.add(txtPosition, 2, 4);
        pane.add(btnSearch, 3, 4);
        pane.add(bPane, 2, 5);
        pane.setHgap(10);
        pane.setVgap(10);
        bPane.setSpacing(5);

        btnFirst.setPrefSize(80, 30);
        btnNext.setPrefSize(80, 30);
        btnPrevious.setPrefSize(80, 30);
        btnLast.setPrefSize(80, 30);

        btnAdd.setPrefSize(80, 30);
        btnUpdate.setPrefSize(80, 30);
        btnDelete.setPrefSize(80, 30);
        btnSearch.setPrefSize(80, 30);

        return pane;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
