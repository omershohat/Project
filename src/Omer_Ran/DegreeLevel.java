package Omer_Ran;

enum DegreeLevel {
    BACHELORS("BA"),             // First degree
    MASTERS("MA"),                 // Second degree
    DOCTOR("Dr."),             // Dr.
    PROFESSOR("PhD.");             // Professor
    private final String description;

    DegreeLevel(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
