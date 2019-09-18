package ba.unsa.etf.rpr.projekat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CheckUp implements Comparable<CheckUp>{
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private LocalTime time;
    private String diagnosis;

    public CheckUp(Doctor doctor, Patient patient, LocalDate date, LocalTime time, String diagnosis) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.diagnosis = diagnosis;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public int compareTo(CheckUp checkUp) {
        int i = date.compareTo(checkUp.date);
        if(i == 0) {
            return time.compareTo(checkUp.time);
        }
        return i;
    }
}
