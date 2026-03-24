package BTTH;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HospitalManager manager = new HospitalManager();
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                System.out.println("KẾT NỐI DATABASE THÀNH CÔNG ");

                manager.updateMedicineStock(conn, 1, 50);

                manager.findMedicinesByPriceRange(conn, 10.0, 100.0);
                manager.calculatePrescriptionTotal(conn, 1);


                System.out.print("Nhập ngày thống kê (yyyy-MM-dd): ");
                String date = sc.nextLine();
                manager.getDailyRevenue(conn, date);

            }
        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
