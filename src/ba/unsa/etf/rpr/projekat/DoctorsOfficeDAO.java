package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DoctorsOfficeDAO {
    private static DoctorsOfficeDAO instance = null;
    private Connection conn;
    private PreparedStatement getBossByUsername, getDoctorByUsername, addDoctor, addDepartment, getNewDepID, getDepID, updateDoctor;

    private DoctorsOfficeDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:doctorsoffice.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement s = conn.createStatement();
            s.execute("select * from boss");
        } catch (SQLException e) {
            regenerateDatabase();
            try {
                Statement s2 = conn.createStatement();
                s2.execute("select * from boss");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            getBossByUsername = conn.prepareStatement("select first_name, last_name, username, password from boss where username=?");
            getDoctorByUsername = conn.prepareStatement("select first_name, last_name, username, password, licence_number, department from doctor where username=?");
            addDepartment = conn.prepareStatement("insert into department values (?,?)");
            addDoctor = conn.prepareStatement("insert into doctor values (?,?,?,?,?,?);");
            getNewDepID = conn.prepareStatement("select max(id)+1 from department");
            getDepID = conn.prepareStatement("select id from department where name=?");
            updateDoctor = conn.prepareStatement("update doctor set first_name=?, last_name=?, department=?, licence_number=? where username=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DoctorsOfficeDAO getInstance() {
        if(instance == null) instance = new DoctorsOfficeDAO();
        return instance;
    }

    public static void removeInstance() {
        if(instance == null) return;
        instance.close();
        instance = null;
    }

    private void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void regenerateDatabase() {
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream("doctorsoffice.db.sql"));
            String sqlQuery = "";
            while (in.hasNext()) {
                sqlQuery += in.nextLine();
                if ( sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boss getBoss(String username) {
        try {
            getBossByUsername.clearParameters();
            getBossByUsername.setString(1, username);
            ResultSet rs = getBossByUsername.executeQuery();
            if(rs.next()) {
                return new Boss(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Doctor getDoctorFromRS(ResultSet rs) {
        try {
            int department = rs.getInt(6);
            PreparedStatement p = conn.prepareStatement("select name from department where id=?");
            p.setInt(1, department);
            ResultSet rs1 = p.executeQuery();
            if(rs1.next()) {
                String departmentName = rs1.getString(1);
                if (departmentName.equals("opća medicina")) {
                    ArrayList<Patient> patients = new ArrayList<>();
                    GeneralPractitioner g = new GeneralPractitioner(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), new ArrayList<Patient>());
                    PreparedStatement p1 = conn.prepareStatement("select * from patient where general_practitioner=?");
                    p1.setString(1, g.getUsername());
                    ResultSet rs2 = p1.executeQuery();
                    while(rs2.next()) {
                        MedicalHistory m = getMedicalHistory(rs2.getInt(4));
                        Patient patient = new Patient(rs2.getString(2), rs2.getString(3), rs2.getString(1), m);
                        patient.setGeneralPractitioner(g);
                        patients.add(patient);
                    }
                    g.setPatients(patients);
                    return g;
                } else {
                    return new SpecializedDoctor(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), departmentName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Doctor getDoctor(String username) {
        try {
            getDoctorByUsername.clearParameters();
            getDoctorByUsername.setString(1, username);
            ResultSet rs = getDoctorByUsername.executeQuery();
            if(rs.next()) {
                return getDoctorFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select username from doctor order by last_name");
            while(rs.next()) {
                Doctor d = getDoctor(rs.getString(1));
                if(d != null) doctors.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public MedicalHistory getMedicalHistory(int number) {
        MedicalHistory result = new MedicalHistory(number);
        try {
            PreparedStatement p = conn.prepareStatement("select name from allergy where patient=?");
            p.setInt(1, number);
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                result.addAllergy(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement p = conn.prepareStatement("select name from disease where patient=?");
            p.setInt(1, number);
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                result.addDisease(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from patient order by last_name");
            while (rs.next()) {
                MedicalHistory m = getMedicalHistory(rs.getInt(4));
                Patient p = new Patient(rs.getString(2), rs.getString(3), rs.getString(1), m);
                Doctor d = getDoctor(rs.getString(5));
                p.setGeneralPractitioner((GeneralPractitioner) d);
                patients.add(p);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public ArrayList<String> getDepartments() {
        ArrayList<String> departments = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select name from department order by name");

            while(rs.next()) {
                departments.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public ArrayList<String> getLicences() {
        ArrayList<String> licences = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select licence_number from doctor order by licence_number");

            while(rs.next()) {
                licences.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return licences;
    }

    private String createUsername(String firstName, String lastName) {
        String result = firstName.charAt(0) + lastName;
        result = result.toLowerCase();

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select username from doctor");
            ArrayList<String> doctors = new ArrayList<>();
            while(rs.next()) {
                doctors.add(rs.getString(1));
            }
            int i = 1;
            while(true) {
                String p = String.valueOf(result);
                p += i;
                if(!doctors.contains(p)) {
                    result = p;
                    break;
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String createPassword() {
        String result = "AutomatskaSifra";

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select password from doctor");
            ArrayList<String> passwords = new ArrayList<>();
            while(rs.next()) {
                passwords.add(rs.getString(1));
            }
            int i = 1;
            while(true) {
                String p = String.valueOf(result);
                p += i;
                if(!passwords.contains(p)) {
                    result = p;
                    break;
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addDoctor(String firstName, String lastName, String department, String licence) {
        int id = 0;
        try {
            getDepID.setString(1, department);
            ResultSet rs = getDepID.executeQuery();
            if(rs.next()){
                id = Integer.parseInt(rs.getString(1));
            } else {
                ResultSet rs1 = getNewDepID.executeQuery();
                if(rs1.next()) {
                    id = rs1.getInt(1);
                }

                PreparedStatement p = conn.prepareStatement("insert into department values (?,?)");
                p.setInt(1, id);
                p.setString(2, department);
                p.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String username = createUsername(firstName, lastName), password = createPassword();

        try {
            addDoctor.setString(1, username);
            addDoctor.setString(2, firstName);
            addDoctor.setString(3, lastName);
            addDoctor.setString(4, password);
            addDoctor.setString(5, licence);
            addDoctor.setInt(6, id);
            addDoctor.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDoctor(String username, String name, String lastName, String department, String licence) {
        int id = 0;
        try {
            getDepID.setString(1, department);
            ResultSet rs = getDepID.executeQuery();
            if(rs.next()){
                id = Integer.parseInt(rs.getString(1));
            } else {
                ResultSet rs1 = getNewDepID.executeQuery();
                if(rs1.next()) {
                    id = rs1.getInt(1);
                }

                PreparedStatement p = conn.prepareStatement("insert into department values (?,?)");
                p.setInt(1, id);
                p.setString(2, department);
                p.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            updateDoctor.setString(1, name);
            updateDoctor.setString(2, lastName);
            updateDoctor.setInt(3, id);
            updateDoctor.setString(4, licence);
            updateDoctor.setString(5, username);
            updateDoctor.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPatient(String pin, String name, String lastName, String username) {
        try {
            PreparedStatement p = conn.prepareStatement("insert into patient values(?,?,?,?,?)");

            PreparedStatement p2 = conn.prepareStatement("select max(medical_history)+1 from patient");
            ResultSet rs = p2.executeQuery();

            if(rs.next()) {
                p.setString(1, pin);
                p.setString(2, name);
                p.setString(3, lastName);
                p.setInt(4, rs.getInt(1));
                p.setString(5, username);
                p.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LocalDate getDate(String date) {
        return LocalDate.of(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10)));
    }

    private LocalTime getTime(String time) {
        return LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)), 0);
    }

    public ArrayList<CheckUp> getCheckUps(String username) {
        ArrayList<CheckUp> result = new ArrayList<>();
        Doctor doctor = getDoctor(username);
        ArrayList<Patient> patients = getPatients();
        try {
            PreparedStatement p = conn.prepareStatement("select patient, diagnosis, date, time from checkup where doctor=?");
            p.setString(1, username);
            ResultSet r = p.executeQuery();
            while(r.next()) {
                int i = r.getInt(1);
                for(Patient p1 : patients) {
                    if(p1.getMedicalHistory().getNumber() == i) {
                        LocalDate date = getDate(r.getString(3));
                        LocalTime time = getTime(r.getString(4));
                        result.add(new CheckUp(doctor, p1, date, time, r.getString(2)));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(result);

        return result;
    }

    public void addCheckUp(String doctor, int patient, LocalDate date, LocalTime time) {
        try {
            PreparedStatement p = conn.prepareStatement("insert into checkup(patient, doctor, diagnosis, date, time) values(?,?,?,?,?)");
            p.setInt(1, patient);
            p.setString(2, doctor);
            p.setString(3, "");
            p.setString(4, date.toString());
            p.setString(5, time.toString());
            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCheckUp(String doctor, int patient, String diagnosis, LocalDate date, LocalTime time) {
        try {
            PreparedStatement p = conn.prepareStatement("update checkup set diagnosis=? where doctor=? and patient=? and date=? and time=?");
            p.setString(1, diagnosis);
            p.setString(2, doctor);
            p.setInt(3, patient);
            p.setString(4, date.toString());
            p.setString(5, time.toString());
            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
