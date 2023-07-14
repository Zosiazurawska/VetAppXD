package pl.kurs.vetapp.exception.visit;

public class NoAvailableVisitException extends Exception{

        public NoAvailableVisitException() {
            super("No available visit for this specialization.");
        }
}
