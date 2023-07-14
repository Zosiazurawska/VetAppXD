package pl.kurs.vetapp.exception.visit;

public class VisitTimeIsNotAvailableException extends Exception{
    public VisitTimeIsNotAvailableException() {
        super("Visit for this period is already booked.");
    }
}
