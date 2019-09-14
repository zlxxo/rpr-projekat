package ba.unsa.etf.rpr.projekat;

public class Patient {

    private String firstName;
    private String lastName;
    private String personalIdentificationNumber;
    private MedicalHistory medicalHistory;
    private GeneralPractitioner generalPractitioner;

    public Patient(String firstName, String lastName, String personalIdentificationNumber, MedicalHistory medicalHistory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.medicalHistory = medicalHistory;
    }

    public void addCheckUp(CheckUp checkUp) {
        medicalHistory.addCheckUp(checkUp);
    }

    public  void addAllergy(String allergy) {
        medicalHistory.addAllergy(allergy);
    }

    public void addDiagnosis(String diagnosis) {
        medicalHistory.addDiagnosis(diagnosis);
    }

    public void addDiseas(String disease) {
        medicalHistory.addDiseas(disease);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public GeneralPractitioner getGeneralPractitioner() {
        return generalPractitioner;
    }

    public void setGeneralPractitioner(GeneralPractitioner generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }
}
