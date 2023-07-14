package pl.kurs.vetapp.exception.doctor;

public class DoctorHaveNotAllParametersException extends Exception{
    public DoctorHaveNotAllParametersException() {
        super("Parameters can not be empty.");
    }
}
