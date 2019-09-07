package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DoctorsOfficeDAO {
    private static DoctorsOfficeDAO instance = null;
    private Connection conn;
    private PreparedStatement getBossByUsername;

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
}
