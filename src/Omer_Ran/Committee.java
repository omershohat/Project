package Omer_Ran;

import static Omer_Ran.ActionStatus.*;
import static Omer_Ran.Utils.removeObject;
import static Omer_Ran.Utils.resizeArr;


public class Committee {
    private String name;
    private Lecturer chairman;
    private Lecturer[] lecturers = new Lecturer[0];
    private int numOfLecturers;
    private StringBuilder sb;


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

    public ActionStatus setChairman(Lecturer chairman) {
        this.chairman = chairman;
        return ActionStatus.SUCCESS;
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

    public ActionStatus assign(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {                   // making sure there is place for new lecturer
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;                       // inserting lecturer to committee array by index
        lecturer.addCommittee(this);
        return ActionStatus.SUCCESS;
    }

    public ActionStatus removeLecturer(Lecturer lecturer) {
        if (removeObject(lecturer, lecturers, numOfLecturers)) {                            // trying to remove lecturer from that committee
            numOfLecturers--;
            return lecturer.removeCommittee(this);                                          // if done - try to remove committee from lecturer's committees array
        }
        return LECTURER_NOT_EXIST_IN_COMM;                                                  // if not done - ERROR
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", chairman = " + chairman.getName() +
                ", lecturers = " + lecturersDisplay() +
                '}';
    }
}
