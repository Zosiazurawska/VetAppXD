package pl.kurs.vetapp.exception.visit;

public class VisitIsNotOnCorrectTimeException extends Exception{
    public VisitIsNotOnCorrectTimeException() {
        super("Visit is not on correct time");
    }
}
