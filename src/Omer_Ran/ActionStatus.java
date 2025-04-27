package Omer_Ran;

enum ActionStatus {
    SUCCESS("Success"),
    LECTURER_EXIST("Lecturer already exists..."),
    LECTURER_NOT_EXIST("Lecturer does not exist..."),
    INVALID_CHOICE("Invalid choice."),
    DEPARTMENT_NOT_EXIST("Department does not exist..."),
    INVALID_SALARY("Invalid salary input. Salary must be 0 or above."),
    COMMITTEE_EXIST("Committee already exists..."),
    DEGREE_LEVEL_TOO_LOW("Lecturer must have a 'Dr.' degree or higher."),
    DEPARTMENT_EXIST("Department already exists..."),
    COMMITTEE_NOT_EXIST("Committee does not exists..."),
    LECTURER_EXIST_IN_COMM("Lecturer already exists in selected committee..."),
    LECTURER_IS_CHAIRMAN("Lecturer already exists in selected committee as the chairman."),
    LECTURER_IN_DEP("Lecturer already exists in selected department..."),
    LECTURER_NOT_EXIST_IN_COMM("Lecturer does not exist in selected committee."),
    COMMITTEE_NOT_EXIST_IN_LEC("Lecturer is not assigned to such committee.");

    private final String description;

    ActionStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
