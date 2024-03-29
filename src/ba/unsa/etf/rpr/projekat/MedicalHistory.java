package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public class MedicalHistory {
    private int number;
    private ArrayList<CheckUp> checkUps;
    private ArrayList<String> allergies;
    private ArrayList<String> diseases;

    public MedicalHistory(int number) {
        this.number = number;
        checkUps = new ArrayList<>();
        allergies = new ArrayList<>();
        diseases = new ArrayList<>();
    }

    public MedicalHistory(int number, ArrayList<CheckUp> checkUps, ArrayList<String> allergies, ArrayList<String> diagnosis, ArrayList<String> diseases) {
        this.number = number;
        this.checkUps = checkUps;
        this.allergies = allergies;
        this.diseases = diseases;
    }

    public void addCheckUp(CheckUp checkUp) {
        checkUps.add(checkUp);
    }

    public  void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void addDisease(String disease) {
        diseases.add(disease);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<CheckUp> getCheckUps() {
        return checkUps;
    }

    public void setCheckUps(ArrayList<CheckUp> checkUps) {
        this.checkUps = checkUps;
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }

    public ArrayList<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(ArrayList<String> diseases) {
        this.diseases = diseases;
    }
}
