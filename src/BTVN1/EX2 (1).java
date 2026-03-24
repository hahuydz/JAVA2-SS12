package BTVN1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import config.DatabaseConfig;

public class EX2 {
    /*
     * PHẦN 1 - PHÂN TÍCH:
     * 1. Tại sao setDouble(), setInt() giúp loại bỏ nỗi lo định dạng: Dữ liệu được truyền đi dưới dạng
     * nhị phân (binary) nguyên bản của kiểu dữ liệu đó thay vì nối chuỗi String.
     * 2. Vai trò Driver: Driver JDBC tự chịu trách nhiệm chuyển đổi sang định dạng mà Database hiểu,
     * bất kể máy tính đang dùng dấu chấm (.) hay dấu phẩy (,) của hệ điều hành.
     */
    private static final String DB_NAME = "Hospital_DB";

    public void updateVitals(int patientId, double temperature, int heartRate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE patient_id = ?";

        try {
            Class.forName(DatabaseConfig.DRIVER);
            conn = DriverManager.getConnection(DatabaseConfig.getURL(DB_NAME), DatabaseConfig.USER, DatabaseConfig.PASS);

            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, temperature);
            pstmt.setInt(2, heartRate);
            pstmt.setInt(3, patientId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Cap nhat thanh cong cho BN ID: " + patientId);
            } else {
                System.out.println("Khong tim thay benh nhan.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PatientVitals().updateVitals(1, 37.5, 80);
    }


}
