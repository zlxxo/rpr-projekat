package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public class SpecializedDoctor extends Doctor {

    private String department;

    public SpecializedDoctor(String firstName, String lastName, String username, String password, String licenceNumber, String department) {
        super(firstName, lastName, username, password, licenceNumber);
        this.department = department;
    }

    public String toString() {
        return super.toString() + " (" + department + ")";
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
