package Omer_Ran.Exceptions;

public class InvalidSalaryException extends InvalidInputException {
    public InvalidSalaryException(float salary) {
        super(salary + " is invalid. Salary must be above 0.");
    }
}
