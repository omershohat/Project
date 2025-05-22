package Omer_Ran;

public class Doctor extends Lecturer {
    private String[] articles = new String[0];
    public Doctor(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department) {
        super(name, id, degreeLevel, major, salary, department);
    }

}
