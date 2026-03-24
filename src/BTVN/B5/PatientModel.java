package BTVN.B5;

public class PatientModel {
    private int id;
    private String name;
    private int age;
    private String department;

    public PatientModel(int id, String name, int age, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Tên: %-15s | Tuổi: %-3d | Khoa: %s", id, name, age, department);
    }
}