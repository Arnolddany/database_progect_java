package com.example.main_db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class HelloController implements Initializable {
    @FXML
    public Label usersLabel;
    @FXML
    public TableView<Database.User> usersTable;
    @FXML
    public TableColumn<Database.User, Integer> idColumn;
    @FXML
    public TableColumn<Database.User, String> loginColumn;
    @FXML
    public TableColumn<Database.User, String> fioColumn;
    @FXML
    public Button usersButton;
    @FXML
    public Button femaleUserButton;
    @FXML
    public Button maleUsersButton;
    @FXML
    public TextField inputLogin;
    @FXML
    public TextField inputFio;
    @FXML
    public PasswordField inputPassword;
    private final ObservableList<String> sex = FXCollections.observableArrayList("Мужской", "Женский");
    private final Alert alert = new Alert(Alert.AlertType.NONE);
    @FXML
    public ComboBox<String> comboSex;
    @FXML
    public Button addUserButton;
    @FXML
    public ComboBox<Integer> comboIdDelete;
    @FXML
    public Button deleteButton;
    @FXML
    public ComboBox<Integer> comboIdEdit;
    @FXML
    public Button editFioButton;
    @FXML
    public TextField inputNewFio;
    @FXML
    public ImageView connectionImage;
    @FXML
    public Label connectMsg;
   // @FXML
  //  public Button checkConnectionButton;
    @FXML
    public CheckBox checkConnectionCheckBox;
    Timer timerConnection = new Timer();
    public void checkConnection() {
        TimerTask checkConnectTimer =  new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!Database.getConnect().isValid(1)) {
                        Database.connected();
                        connectionImage.setVisible(true);
                        connectMsg.setVisible(false);

                        usersButton.setDisable(false);
                        femaleUserButton.setDisable(false);
                        maleUsersButton.setDisable(false);
                        addUserButton.setDisable(false);
                        deleteButton.setDisable(false);
                        editFioButton.setDisable(false);
                        inputLogin.setDisable(false);
                        inputPassword.setDisable(false);
                        inputFio.setDisable(false);
                        inputNewFio.setDisable(false);
                    }
                } catch (Throwable e) {
                    System.out.println(e.getMessage());
                    connectionImage.setVisible(false);
                    connectMsg.setVisible(true);

                    usersButton.setDisable(true);
                    femaleUserButton.setDisable(true);
                    maleUsersButton.setDisable(true);
                    addUserButton.setDisable(true);
                    deleteButton.setDisable(true);
                    editFioButton.setDisable(true);
                    inputLogin.setDisable(true);
                    inputPassword.setDisable(true);
                    inputFio.setDisable(true);
                    inputNewFio.setDisable(true);
                }
            }
        };
        timerConnection.schedule(checkConnectTimer, 2000, 15000);
    }
    private void setRulesTextField() {
        inputLogin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputLogin.getText().length() <= 20) {
                if (!newValue.matches("\\s0-9a-zA-Z-_*")) {
                    inputLogin.setText(newValue.replaceAll("[^\\s0-9a-zA-Z-_]", ""));
                }
            } else {
                inputLogin.setText(oldValue);
            }
        });
        inputPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputPassword.getText().length() <= 16) {
                if (!newValue.matches("\\s0-9a-zA-Z*")) {
                    inputPassword.setText(newValue.replaceAll("[^\\s0-9a-zA-Z]", ""));
                }
            } else {
                inputPassword.setText(oldValue);
            }
        });
        inputFio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sа-яА-Я*")) {
                inputFio.setText(newValue.replaceAll("[^\\sа-яА-Я]", ""));
            }
        });
        inputNewFio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sа-яА-Я*")) {
                inputNewFio.setText(newValue.replaceAll("[^\\sа-яА-Я]", ""));
            }
        });
    }
    private void filComboBox() {
        comboSex.getItems().setAll(sex);
        comboSex.setValue("Женский");
        usersTable.setItems(Database.getAllUsers());
        comboIdDelete.getItems().setAll(Database.getIdUser());
        comboIdDelete.setValue(Database.getIdUser().getFirst());
        comboIdEdit.getItems().setAll(Database.getIdUser());
        comboIdEdit.setValue(Database.getIdUser().getFirst());
    }
    public void setColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
    }
    public void viewAllUsers() {
        connectionImage.setVisible(true);
        connectMsg.setVisible(false);
        if (!usersTable.getItems().isEmpty()) {
            usersTable.getItems().clear();
        }
        setColumns();
        usersTable.setItems(Database.getAllUsers());
    }
    public void viewFemaleUsers() {
        if (!usersTable.getItems().isEmpty()) {
            usersTable.getItems().clear();
        }
        setColumns();
        usersTable.setItems(Database.getFemaleUsers());
    }
    public void viewMaleUsers() {
        if (!usersTable.getItems().isEmpty()) {
            usersTable.getItems().clear();
        }
        setColumns();
        usersTable.setItems(Database.getMaleUsers());
    }
    public void addUser() {
        if (inputLogin.getText().isEmpty() && inputPassword.getText().isEmpty() && inputFio.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля!");
            alert.show();
        } else {
            if (Database.getLogin(String.valueOf(inputLogin.getText())) == 0) {
                Database.insertUser(String.valueOf(inputLogin.getText()), String.valueOf(inputPassword.getText()), String.valueOf(inputFio.getText()), comboSex.getSelectionModel().getSelectedItem());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Пользователь добавлен!");
                alert.show();
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Такой логин уже существует!");
                alert.show();
            }
        }
    }
    public void deleteUser() {
        Database.deleteUser(comboIdDelete.getSelectionModel().getSelectedItem());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Пользователь удален!");
        alert.show();
    }
    public void updateFioUser(){
        String oldFio = Database.getFio(comboIdEdit.getSelectionModel().getSelectedItem());
        Database.updateFioUser(comboIdEdit.getSelectionModel().getSelectedItem(), String.valueOf(inputNewFio.getText()));
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("ФИО пользователя №" + comboIdEdit.getSelectionModel().getSelectedItem() + " " + oldFio  + " изменено на " + inputNewFio.getText() + " !");
        alert.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert addUserButton != null : "fx:id=\"addUserButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert comboIdDelete != null : "fx:id=\"comboIdDelete\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert comboIdEdit != null : "fx:id=\"comboIdEdit\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert comboSex != null : "fx:id=\"comboSex\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert editFioButton != null : "fx:id=\"editFioButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert femaleUserButton != null : "fx:id=\"femaleUserButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert fioColumn != null : "fx:id=\"fioColumn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert inputFio != null : "fx:id=\"inputFio\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert inputLogin != null : "fx:id=\"inputLogin\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert inputNewFio != null : "fx:id=\"inputNewFio\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert inputPassword != null : "fx:id=\"inputPassword\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert loginColumn != null : "fx:id=\"loginColumn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert maleUsersButton != null : "fx:id=\"maleUsersButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert usersButton != null : "fx:id=\"usersButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert usersLabel != null : "fx:id=\"usersLabel\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert usersTable != null : "fx:id=\"usersTable\" was not injected: check your FXML file 'hello-view.fxml'.";

        setRulesTextField();
        setColumns();
        filComboBox();

//            Database.getTable().forEach(user -> table.appendText(user.toString()));
    }

}
