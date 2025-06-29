package ma.enset.gestionconsultationbdcc.entities;

import java.util.Date;

public class Consultation {
    private int id;
    private String date;
    private String description;
    public Patient patient;

    public Consultation() {
    }

    public Consultation(String date, String description, Patient patient) {
        this.date = date;
        this.description = description;
        this.patient = patient;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}