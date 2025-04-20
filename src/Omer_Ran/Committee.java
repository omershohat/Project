package Omer_Ran;

import java.util.Arrays;

public class Committee {
    private String name;
    private Lecturer chairman;
    private Lecturer[] lecturers = new Lecturer[0];
    private int numOfLecturers;

    public Committee() {
    }

    public Committee(String name, Lecturer chairman) {
        this.name = name;
        this.chairman = chairman;
    }

    public int getNumOfLecturers() {
        return numOfLecturers;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public Lecturer getChairman() {
        return chairman;
    }

    public String getLecturersNames() {
        StringBuilder sb = new StringBuilder();
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

    public boolean setChairman(Lecturer chairman) {
        if (chairman.getDegreeLevel().ordinal() >= 2) {
            this.chairman = chairman;
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void addLecturer(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {
            lecturers = copy(lecturers, numOfLecturers == 0 ? 2 : numOfLecturers * 2);
        }
        lecturers[numOfLecturers++] = lecturer;
        return;
    }

    private static Lecturer[] copy(Lecturer[] arr, int size) {
        Lecturer[] temp = new Lecturer[size];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    public boolean removeLecturer(String lecturerName) {
        for (int i = 0; i < numOfLecturers; i++) {                          // checking for existing lecturer
            if (lecturers[i].getName().equals(lecturerName)) {
                for (int j = i; j < numOfLecturers - 1; j++) {              // if exist - from that lecturer index, shift all lecturers left
                    lecturers[j] = lecturers[j + 1];
                }
                lecturers[numOfLecturers - 1] = null;                       // remove last one
                numOfLecturers--;
                return true;
            }
        }
        return false; // Not found
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", chairman = " + chairman.getName() +
                ", lecturers = " + this.getLecturersNames() +
                '}';
    }
}
