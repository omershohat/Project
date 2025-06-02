package Omer_Ran;

import java.util.Comparator;

import static Omer_Ran.Utils.removeObject;
import static Omer_Ran.Utils.resizeArr;


public class Committee implements Nameable, Cloneable{
    private String name;
    private Lecturer chairman;
    private Lecturer[] lecturers = new Lecturer[0];
    private int numOfLecturers;
    private StringBuilder sb;

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

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public int getNumOfLecturers() {
        return numOfLecturers;
    }

    private String lecturersDisplay() {
        sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < numOfLecturers; i++) {
            if ((lecturers[i] != null) && (!lecturers[i].getName().equals(chairman.getName()))) {
                if (i == numOfLecturers - 1) {
                    sb.append(lecturers[i].getName());
                } else {
                    sb.append(lecturers[i].getName()).append(",");
                }
            }

        }
        sb.append("]");
        return sb.toString();
    }

    public void assign(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {                   // making sure there is place for new lecturer
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;                       // inserting lecturer to committee array by index
        lecturer.addCommittee(this);
    }

    public void removeLecturer(Lecturer lecturer) {
        if (removeObject(lecturer, lecturers, numOfLecturers)) {        // trying to remove lecturer from that committee
            numOfLecturers--;
            lecturer.removeCommittee(this);                             // if done - try to remove committee from lecturer's committees array
            return;
        }
        throw new NotExistException(lecturer, this);                    // if not done - not exist - EXCEPTION
    }

    public int getSumOfArticles() {
        int sum = 0 ;
        for (int i = 0; i < numOfLecturers; i++) {
            if (lecturers[i] instanceof Doctor lec) {
                sum += lec.getArticles().length;
            }
        }
        return sum;
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
        c1.lecturers = lecturers.clone();                   // we want a new list with the same pointers to lecturers
        for (int i = 0; i < numOfLecturers ; i++) {
            c1.lecturers[i].addCommittee(c1);               // assigning every lecturer in new clone, to that clone
        }
        c1.setName(name + "-new");                          // setting clone name
        return c1;
    }
}
