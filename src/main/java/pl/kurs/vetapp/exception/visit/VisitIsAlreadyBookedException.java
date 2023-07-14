package pl.kurs.vetapp.exception.visit;

public class VisitIsAlreadyBookedException extends Exception{

    public VisitIsAlreadyBookedException() {
        super("Visit for this time is already booked.");
    }
}
