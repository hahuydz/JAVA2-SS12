package BTTH;

import java.sql.*;

public class HospitalManager {

    public void updateMedicineStock(Connection conn, int id, int addedQuantity) throws SQLException {
        String sql = "UPDATE medicines SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, addedQuantity);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("=> Cập nhật kho thành công!");
            else System.out.println("=> Không tìm thấy thuốc có ID: " + id);
        }
    }

    public void findMedicinesByPriceRange(Connection conn, double minPrice, double maxPrice) throws SQLException {
        String sql = "SELECT * FROM medicines WHERE price BETWEEN ? AND ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, minPrice);
            pstmt.setDouble(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("--- Danh sách thuốc từ " + minPrice + " đến " + maxPrice + " ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Tên: %-15s | Giá: %.2f | Kho: %d\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
            }
        }
    }


    public void calculatePrescriptionTotal(Connection conn, int p_id) throws SQLException {
        String sql = "{call CalculatePrescriptionTotal(?, ?)}";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, p_id); // Tham số IN
            cstmt.registerOutParameter(2, Types.DECIMAL); // Tham số OUT

            cstmt.execute();

            double total = cstmt.getDouble(2);
            System.out.println("=> Tổng tiền đơn thuốc ID " + p_id + " là: " + total);
        }
    }


    public void getDailyRevenue(Connection conn, String dateStr) throws SQLException {
        String sql = "{call GetDailyRevenue(?, ?)}";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setDate(1, Date.valueOf(dateStr));
            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();

            double revenue = cstmt.getDouble(2);
            System.out.println("=> Doanh thu ngày " + dateStr + " là: " + revenue);
        }
    }
}