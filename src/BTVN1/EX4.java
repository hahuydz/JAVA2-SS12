package BTVN1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConfig;

/*
 * PHẦN 1 - PHÂN TÍCH:
 * 1. Sự lãng phí tài nguyên: Khi dùng Statement trong vòng lặp 1.000 lần, Database Server buộc phải
 * thực hiện lại quy trình: Kiểm tra cú pháp (Parse), Kiểm tra quyền hạn, và Lập kế hoạch thực thi (Execution Plan)
 * cho 1.000 câu lệnh khác nhau (do chuỗi SQL thay đổi mỗi lần nối thêm dữ liệu).
 * 2. Tác động: Điều này tiêu tốn cực lớn tài nguyên CPU và RAM của Database, khiến tốc độ xử lý chậm đi hàng chục lần.
 * * PHẦN 2 - THỰC THI (Tối ưu hóa):
 * Sử dụng PreparedStatement để biên dịch cấu trúc SQL 1 lần duy nhất ngoài vòng lặp.
 * Trong vòng lặp chỉ truyền tham số và thực thi, giúp giảm tải tối đa cho Database Server.
 */

public class EX4 {
    private static final String DB_NAME = "Hospital_DB";

    static class TestResult {
        private String data;
        public TestResult(String data) { this.data = data; }
        public String getData() { return data; }
    }

    public void insertBulkResults(List<TestResult> list) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Results(data) VALUES (?)";

        try {
            Class.forName(DatabaseConfig.DRIVER);
            conn = DriverManager.getConnection(DatabaseConfig.getURL(DB_NAME), DatabaseConfig.USER, DatabaseConfig.PASS);

            long startTime = System.currentTimeMillis();

            pstmt = conn.prepareStatement(sql);

            for (TestResult tr : list) {
                pstmt.setString(1, tr.getData());
                pstmt.executeUpdate();
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Nạp 1.000 kết quả thành công!");
            System.out.println("Thời gian thực hiện: " + (endTime - startTime) + " ms");

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
        LabResultPerformance app = new LabResultPerformance();

        List<TestResult> list = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            list.add(new TestResult("Kết quả xét nghiệm máu số " + i));
        }

        app.insertBulkResults(list);
    }
}
