package Omer_Ran.Exceptions;

import Omer_Ran.Committee;
import Omer_Ran.Lecturer;

public class QualificationException extends InvalidInputException {
    public QualificationException(Lecturer lecturer) {
        super(lecturer.getName() + "'s degree level is too low.");
    }

    public QualificationException(Lecturer lecturer, Committee committee) {
        super(lecturer.getName() + " is already the chairman of '" + committee.getName() + "' committee.");
    }
}