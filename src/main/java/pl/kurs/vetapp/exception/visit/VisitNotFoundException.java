package pl.kurs.vetapp.exception.visit;

public class VisitNotFoundException extends Exception {

    public VisitNotFoundException(Long id) {
        super("Visit with ID: " + id + " not found!");
    }
}
