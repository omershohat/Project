package Omer_Ran;

public class InvalidSalaryException extends InvalidInputException {
    public InvalidSalaryException(float salary) {
        super(salary + " is invalid. Salary must be above 0.");
    }
}
