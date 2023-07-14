package pl.kurs.vetapp.exception.visit;

public class VisitHaveNotAllParametersException extends Exception {
    public VisitHaveNotAllParametersException() {
        super("Parameters can not be empty.");
    }
}


