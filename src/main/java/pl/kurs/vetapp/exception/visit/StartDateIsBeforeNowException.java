package pl.kurs.vetapp.exception.visit;

public class StartDateIsBeforeNowException extends Exception {
    public StartDateIsBeforeNowException() {
        super("Start date of visit can not be before today!");
    }
}
