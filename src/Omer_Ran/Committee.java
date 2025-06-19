package Omer_Ran;

import Omer_Ran.Exceptions.NotExistException;

import java.io.Serializable;
import java.util.ArrayList;

public class Committee implements Nameable, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private DegreeLevel degreeLevel;
    private String name;
    private Lecturer chairman;
    private ArrayList<Lecturer> lecturers = new ArrayList<>();

    public Committee(String name, Lecturer chairman, DegreeLevel degreeLevel) {
        this.name = name;
        this.chairman = chairman;
        this.degreeLevel = degreeLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Committee(String name, Lecturer chairman) {
        this.name = name;
        this.chairman = chairman;
    }

    public Committee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Lecturer getChairman() {
        return chairman;
    }

    public void setChairman(Lecturer chairman) {
        this.chairman = chairman;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public int getNumOfLecturers() {
        return lecturers.size();
    }

    private String lecturersDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < lecturers.size(); i++) {
            Lecturer lec = lecturers.get(i);
            if (!lec.getName().equals(chairman.getName())) {
                if (i == lecturers.size() - 1) {
                    sb.append(lec.getName());
                } else {
                    sb.append(lec.getName()).append(", ");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void assign(Lecturer lecturer) {
        lecturers.add(lecturer);                       // inserting lecturer to committee array by index
        lecturer.addCommittee(this);
    }

    public void removeLecturer(Lecturer lecturer) {
        if (lecturers.remove(lecturer)) {        // trying to remove lecturer from that committee
            lecturer.removeCommittee(this);                             // if done - try to remove committee from lecturer's committees array
            return;
        }
        throw new NotExistException(lecturer, this);                    // if not done - not exist - EXCEPTION
    }

    public int getSumOfArticles() {
        int sum = 0;
        for (Lecturer lecturer : lecturers) {
            if (lecturer instanceof Doctor lec) {
                sum += lec.getArticles().length;
            }
        }
        return sum;
    }

    public void setDegreeLevel(DegreeLevel degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Committee com) {
            return com.name.equals(name);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", chairman = " + chairman.getName() +
                ", lecturers = " + lecturersDisplay() +
                '}';
    }

    @Override
    protected Committee clone() throws CloneNotSupportedException {
        Committee c1 = (Committee) super.clone();           // cloning
        c1.lecturers = (ArrayList<Lecturer>) lecturers.clone();                   // we want a new list with the same pointers to lecturers
        for (int i = 0; i < lecturers.size(); i++) {
            c1.lecturers.get(i).addCommittee(c1);               // assigning every lecturer in new clone, to that clone
        }
        c1.setName(name + "-new");                          // setting clone name
        return c1;
    }


}
