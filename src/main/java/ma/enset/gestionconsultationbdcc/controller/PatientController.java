package ma.enset.gestionconsultationbdcc.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML private TextField textFieldNom;
    @FXML private TextField textFieldPrenom;
    @FXML private TextField textFieldTel;
    @FXML private TextField textFieldSearch;
    @FXML private TableView<Patient> tableViewPatients;
    @FXML private TableColumn<Patient, Integer> columnId;
    @FXML private TableColumn<Patient, String> columnNom;
    @FXML private TableColumn<Patient, String> columnPrenom;
    @FXML private TableColumn<Patient, String> columnTel;
    @FXML private Label labelSuccess;
    private ICabinetService cabinetService;
    private ObservableList<Patient> patients;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = CabinetService.getInstance();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        patients = cabinetService.getPatients();
        tableViewPatients.setItems(patients);
        loadPatient();
        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            patients.setAll(cabinetService.searchByQuery(newValue));
        });
        tableViewPatients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textFieldNom.setText(newValue.getNom());
                textFieldPrenom.setText(newValue.getPrenom());
                textFieldTel.setText(newValue.getTelephone());
            }
            else {
                textFieldNom.clear();
                textFieldPrenom.clear();
                textFieldTel.clear();
            }
        });
    }

    public void ajouterPatient(){
        Patient patient = new Patient();
        patient.setNom(textFieldNom.getText());
        patient.setPrenom(textFieldPrenom.getText());
        patient.setTelephone(textFieldTel.getText());
        cabinetService.ajouterPatient(patient);
        loadPatient();
        labelSuccess.setText("Le patient a été bien ajouté");
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldTel.clear();
    }

    public void supprimerPatient(){
        Patient patient = tableViewPatients.getSelectionModel().getSelectedItem();
        cabinetService.supprimerPatient(patient);
        loadPatient();
        labelSuccess.setText("Le patient a été bien supprimé");
    }

    public void modifierPatient(){
        Patient patient = tableViewPatients.getSelectionModel().getSelectedItem();
        patient.setNom(textFieldNom.getText());
        patient.setPrenom(textFieldPrenom.getText());
        patient.setTelephone(textFieldTel.getText());
        cabinetService.modifierPatient(patient);
        loadPatient();
        labelSuccess.setText("Le patient a été bien modifié");
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldTel.clear();
    }

    public void loadPatient(){
        cabinetService.refreshPatients();
    }

}