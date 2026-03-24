package BTVN.B5;

import java.util.Scanner;

public class RHMS_Main {
    public static void main(String[] args) {
        HospitalDAO dao = new HospitalDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- RIKKEI-HOSPITAL MANAGEMENT ---");
            System.out.println("1. Danh sách bệnh nhân\n2. Tiếp nhận bệnh nhân\n3. Cập nhật bệnh án\n4. Xuất viện & Tính phí\n5. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            try {
                if (choice == 5) break;
                switch (choice) {
                    case 1:
                        dao.getAll().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Tên: "); String name = sc.nextLine();
                        System.out.print("Tuổi: "); int age = Integer.parseInt(sc.nextLine());
                        System.out.print("Khoa: "); String dep = sc.nextLine();
                        dao.addPatient(name, age, dep);
                        break;
                    case 3:
                        System.out.print("ID: "); int idU = Integer.parseInt(sc.nextLine());
                        System.out.print("Tình trạng mới: "); String con = sc.nextLine();
                        dao.updateCondition(idU, con);
                        break;
                    case 4:
                        System.out.print("ID bệnh nhân xuất viện: "); int idF = Integer.parseInt(sc.nextLine());
                        System.out.printf("Tổng viện phí: %,.2f VND\n", dao.calculateFee(idF));
                        break;
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }
}
