package com.example.main_db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


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
    @FXML
    private final ObservableList<String> sex = FXCollections.observableArrayList("Мужской", "Женский");
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    public ComboBox<String> comboSex;
    public Button addUserButton;
    public ComboBox<Integer> comboIdDelete;
    public Button deleteButton;
    public ComboBox<Integer> comboIdEdit;
    public Button editFioButton;
    public TextField inputNewFio;
    public ImageView connectionImage;
    public Label connectMsg;
    public Button checkConnectionButton;

    public void checkConnection() throws SQLException {
        try {
            if (!Database.getConnect().isValid(1)) {
                Database.connected();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Подключение востановлено!");
                alert.show();
                connectionImage.setVisible(true);
                connectMsg.setVisible(false);
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Подключение в норме!");
                alert.show();
            }
        } catch (Throwable e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Невозможно восстановить подключение! Попробуйте позже!");
            alert.show();
            System.out.println(e.getMessage());
            connectionImage.setVisible(false);
            connectMsg.setVisible(true);
        }

    }
    private void errorConnect() {
        connectionImage.setVisible(false);
        connectMsg.setVisible(true);
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText("Разорвано подключение с базой данных!");
        alert.show();
    }

    public void setColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
    }
    public void viewAllUsers() throws SQLException {
        if (Database.getConnect().isValid(1)) {
            connectionImage.setVisible(true);
            connectMsg.setVisible(false);
            if (!usersTable.getItems().isEmpty()) {
                usersTable.getItems().clear();
            }
            setColumns();
            usersTable.setItems(Database.getAllUsers());
        } else {
           errorConnect();
        }
        

    }
    public void viewFemaleUsers() throws SQLException {
        if (Database.getConnect().isValid(1)) {
            if (!usersTable.getItems().isEmpty()) {
                usersTable.getItems().clear();
            }
            setColumns();
            usersTable.setItems(Database.getFemaleUsers());
        } else {
            errorConnect();
        }
    }
    public void viewMaleUsers() throws SQLException {
        if (Database.getConnect().isValid(1)) {
            if (!usersTable.getItems().isEmpty()) {
                usersTable.getItems().clear();
            }
            setColumns();
            usersTable.setItems(Database.getMaleUsers());
        } else {
            errorConnect();
        }

    }
    public void addUser() throws SQLException {
        if (Database.getConnect().isValid(1)) {
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
        } else {
            errorConnect();
        }


    }
    public void deleteUser() throws SQLException {
        if (Database.getConnect().isValid(1)) {
            Database.deleteUser(comboIdDelete.getSelectionModel().getSelectedItem());
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Пользователь удален!");
            alert.show();
        } else {
            errorConnect();
        }

    }
    public void updateFioUser() throws SQLException {
        if (Database.getConnect().isValid(1)) {
            String oldFio = Database.getFio(comboIdEdit.getSelectionModel().getSelectedItem());
            Database.updateFioUser(comboIdEdit.getSelectionModel().getSelectedItem(), String.valueOf(inputNewFio.getText()));
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("ФИО пользователя №" + comboIdEdit.getSelectionModel().getSelectedItem() + " " + oldFio  + " изменено на " + inputNewFio.getText() + " !");
            alert.show();
        } else {
            errorConnect();
        }
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

        comboSex.getItems().setAll(sex);
        comboSex.setValue("Женский");
        setColumns();

        usersTable.setItems(Database.getAllUsers());
        comboIdDelete.getItems().setAll(Database.getIdUser());
        comboIdDelete.setValue(Database.getIdUser().getFirst());
        comboIdEdit.getItems().setAll(Database.getIdUser());
        comboIdEdit.setValue(Database.getIdUser().getFirst());

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

//            Database.getTable().forEach(user -> table.appendText(user.toString()));
    }

}
