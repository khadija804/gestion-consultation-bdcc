package ma.enset.gestionconsultationbdcc.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {
    @FXML private DatePicker dateConsultation;
    @FXML
    private ComboBox<Patient> comboPatient;
    @FXML private TextArea textDescription;
    @FXML private TableView<Consultation> tableConsultation;
    @FXML private TableColumn<Consultation, Integer> columnId;
    @FXML private TableColumn<Consultation, Date> columnDate;
    @FXML private TableColumn<Consultation, String> columnDescription;
    @FXML private TableColumn<Consultation, Integer> columnPatient;
    @FXML private Label labelSuccess;
    private ICabinetService cabinetService;
    private ObservableList<Patient> patients;
    private ObservableList<Consultation> consultations = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = CabinetService.getInstance();
        patients = cabinetService.getPatients();
        comboPatient.setItems(patients);
        columnId.setCellValueFactory(new PropertyValueFactory<>("id_consultation"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date_consultation"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnPatient.setCellValueFactory(celldate -> {
            Patient patient = celldate.getValue().getPatient();
            return new SimpleIntegerProperty(patient.getId_patient()).asObject();
        });
        tableConsultation.setItems(consultations);
        loadConsultation();
        tableConsultation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                textDescription.setText(newValue.getDescription());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateStr = newValue.getDate();
                dateConsultation.setValue(LocalDate.parse(dateStr, formatter));
                Patient selectedPatient = null;
                for (Patient patient : comboPatient.getItems()) {
                    if (patient.getId_patient() == newValue.getPatient().getId_patient()) {
                        selectedPatient = patient;
                        break;
                    }
                }
                comboPatient.getSelectionModel().select(selectedPatient);
            }
            else
            {
                textDescription.clear();
                dateConsultation.setValue(null);
                comboPatient.getSelectionModel().clearSelection();
            }
        });
    }

    public void ajouterConsultation(){
        Consultation consultation = new Consultation();
        consultation.setDescription(textDescription.getText());
        consultation.setDate(Date.valueOf(dateConsultation.getValue()).toString());
        consultation.setPatient(comboPatient.getSelectionModel().getSelectedItem());
        cabinetService.ajouterConsultation(consultation);
        loadConsultation();
        textDescription.clear();
        dateConsultation.setValue(null);
        comboPatient.getSelectionModel().clearSelection();
        labelSuccess.setText("La consultation a été bien ajouté");
    }

    public void supprimerConsultation(){
        Consultation consultation = tableConsultation.getSelectionModel().getSelectedItem();
        cabinetService.supprimerConsultation(consultation);
        loadConsultation();
        labelSuccess.setText("La consultation a été bien supprimé");

    }
    public void modifierConsultation(){
        Consultation consultation = tableConsultation.getSelectionModel().getSelectedItem();
        consultation.setDescription(textDescription.getText());
        consultation.setDate(dateConsultation.getValue().toString());
        consultation.setPatient(comboPatient.getSelectionModel().getSelectedItem());
        cabinetService.modifierConsultation(consultation);
        loadConsultation();
        textDescription.clear();
        dateConsultation.setValue(null);
        comboPatient.getSelectionModel().clearSelection();
        labelSuccess.setText("La consultation a été bien modifié");
    }

    public void loadConsultation(){
        consultations.setAll(cabinetService.getAllConsultation());
    }
}