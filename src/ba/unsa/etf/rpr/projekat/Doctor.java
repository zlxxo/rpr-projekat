package ba.unsa.etf.rpr.projekat;

public class Doctor extends Employee{

    private String licenceNumber;

    public Doctor(String firstName, String lastName, String username, String password, String licenceNumber) {
        super(firstName, lastName, username, password);
        this.licenceNumber = licenceNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }
}
