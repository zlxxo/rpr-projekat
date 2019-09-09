package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public class GeneralPractitioner extends Doctor {

    private ArrayList<Patient> patients;

    public GeneralPractitioner(String firstName, String lastName, String username, String password, String licenceNumber, ArrayList<Patient> patients) {
        super(firstName, lastName, username, password, licenceNumber);
        this.patients = patients;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }
}
