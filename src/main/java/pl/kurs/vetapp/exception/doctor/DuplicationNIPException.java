package pl.kurs.vetapp.exception.doctor;

public class DuplicationNIPException extends Exception {
    public DuplicationNIPException(String NIP) {
        super("Doctor with NIP: " + NIP + " already exist.");
    }
}
