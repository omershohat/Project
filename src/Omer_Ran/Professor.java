package Omer_Ran;

public class Professor extends Doctor {
    private String certifyingInst;
    public Professor(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department, String[] articles, String certifyingInst) {
        super(name, id, degreeLevel, major, salary, department, articles);
        this.certifyingInst = certifyingInst;
    }

    @Override
    public String toString() {
        return super.toString() + ", certifying institution = " + certifyingInst;
    }
}
