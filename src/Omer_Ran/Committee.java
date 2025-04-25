package Omer_Ran;

import static Omer_Ran.ActionStatus.LECTURER_NOT_EXIST_IN_COMM;
import static Omer_Ran.Utils.resizeArr;

public class Committee {
    private String name;
    private Lecturer chairman;
    private Lecturer[] lecturers = new Lecturer[0];
    private int numOfLecturers;


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

    public ActionStatus assign(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {                   // making sure there is place for new lecturer
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;                     // inserting lecturer to committee array by index
        return ActionStatus.SUCCESS;
    }

    public ActionStatus removeLecturer(Lecturer lecturer) {
        for (int i = 0; i < numOfLecturers; i++) {                          // checking for existing lecturer
            if (lecturers[i] == (lecturer)) {
                for (int j = i; j < numOfLecturers - 1; j++) {              // if exist - from that lecturer index, shift all lecturers left
                    if (lecturers[j] == null) {
                        break;
                    }
                    lecturers[j] = lecturers[j + 1];
                }
                lecturers[numOfLecturers - 1] = null;                       // remove last doubled lecturer
                numOfLecturers--;
                return ActionStatus.SUCCESS;
            }
        }
        return LECTURER_NOT_EXIST_IN_COMM;                                  // if not exists - ERROR
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", chairman = " + chairman.getName() +
                ", lecturers = " + getLecturersNames() +
                '}';
    }
}
