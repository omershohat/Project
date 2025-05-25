package Omer_Ran;

public class ChairmanException extends InvalidInputException {
    public ChairmanException(Lecturer lecturer) {
        super(lecturer.getName() + "'s degree level is too low.");
    }
    public ChairmanException(Lecturer lecturer, Committee committee) {
        super(lecturer.getName() + " is already the chairman of '" + committee.getName() + "' committee.");
    }
}
