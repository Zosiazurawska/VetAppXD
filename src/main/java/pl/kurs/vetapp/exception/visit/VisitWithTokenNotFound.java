package pl.kurs.vetapp.exception.visit;

public class VisitWithTokenNotFound extends Exception{
    public VisitWithTokenNotFound(String token) {
        super("Visit with toke: " + token + " not found.");
    }
}
