import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/game_info_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection connect() {
        try {
            // Tidak wajib lagi di Java 8+, tapi tetap aman untuk kompatibilitas
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC tidak ditemukan.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal.");
            System.out.println("Pesan error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}