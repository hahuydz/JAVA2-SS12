package BTVN1;

public class EX3 {
    /*
     * PHẦN 1 - PHÂN TÍCH:
     * 1. Tại sao bắt buộc gọi registerOutParameter(): JDBC cần biết trước kiểu dữ liệu trả về để
     * cấp phát bộ nhớ và chuẩn bị trình ánh xạ phù hợp trước khi thực thi thủ tục.
     * 2. Đăng ký kiểu DECIMAL: Trong Java phải sử dụng hằng số Types.DECIMAL trong lớp java.sql.Types
     * để tương ứng với kiểu DECIMAL trong SQL.
     */
    private static final String DB_NAME = "Hospital_DB";

    public void getSurgeryFee(int surgeryId) {
        Connection conn = null;
        CallableStatement cstmt = null;
        String sql = "{call GET_SURGERY_FEE(?, ?)}";

        try {
            Class.forName(DatabaseConfig.DRIVER);
            conn = DriverManager.getConnection(DatabaseConfig.getURL(DB_NAME), DatabaseConfig.USER, DatabaseConfig.PASS);

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, surgeryId);
            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();

            double totalCost = cstmt.getDouble(2);
            System.out.printf("Ma ca: %d | Tong chi phi: %,.2f VND\n", surgeryId, totalCost);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SurgeryService().getSurgeryFee(505);
    }


}
