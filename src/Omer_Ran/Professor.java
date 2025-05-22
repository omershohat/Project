package Omer_Ran;

public class Professor extends Doctor {
    private String certifyingInst;
    public Professor(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department, String certifyingInstitution) {
        super(name, id, degreeLevel, major, salary, department);
    }
}
