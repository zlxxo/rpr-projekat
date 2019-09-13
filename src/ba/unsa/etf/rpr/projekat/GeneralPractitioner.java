package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public class GeneralPractitioner extends Doctor {

    private ArrayList<Patient> patients;
    private String department = "opća medicina";

    public GeneralPractitioner(String firstName, String lastName, String username, String password, String licenceNumber, ArrayList<Patient> patients) {
        super(firstName, lastName, username, password, licenceNumber);
        this.patients = patients;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public String getDepartment() {
        return department;
    }
}
