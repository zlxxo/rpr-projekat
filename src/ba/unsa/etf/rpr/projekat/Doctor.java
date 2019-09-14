package ba.unsa.etf.rpr.projekat;

public class Doctor extends Employee{

    private String licenceNumber;
    private String department;

    public Doctor() {
        this("", "", "", "", "");
    }

    public Doctor(String firstName, String lastName, String username, String password, String licenceNumber) {
        super(firstName, lastName, username, password);
        this.licenceNumber = licenceNumber;
    }

    @Override
    public String toString() {
        return "dr. " + super.toString();
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getDepartment() {
        return "";
    }
}
