package BTVN1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConfig;

public class EX1 {

    /*
     * PHẦN 1 - PHÂN TÍCH:
     * 1. PreparedStatement là "tấm khiên" vì: Nó tách biệt hoàn toàn cấu trúc câu lệnh SQL và dữ liệu người dùng.
     * Dữ liệu người dùng chỉ được coi là tham số thuần túy, không thể thay đổi logic câu lệnh.
     * 2. Cơ chế Pre-compiled: Câu lệnh được gửi lên DB biên dịch trước với các dấu ?.
     * Sau đó tham số mới truyền vào sau, giúp bảo vệ an toàn tuyệt đối trước SQL Injection.
     */
        private static final String DB_NAME = "Hospital_DB";

        public boolean login(String doctorCode, String password) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT full_name FROM Doctors WHERE doctor_code = ? AND password = ?";

            try {
                Class.forName(DatabaseConfig.DRIVER);
                conn = DriverManager.getConnection(DatabaseConfig.getURL(DB_NAME), DatabaseConfig.USER, DatabaseConfig.PASS);

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, doctorCode);
                pstmt.setString(2, password);

                rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Dang nhap thanh cong! Chao mung BS: " + rs.getString("full_name"));
                    return true;
                } else {
                    System.out.println("LOI: Sai tai khoan hoac mat khau.");
                    return false;
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            DoctorLogin app = new DoctorLogin();
            app.login("DOC001", "' OR '1'='1");
        }

}
