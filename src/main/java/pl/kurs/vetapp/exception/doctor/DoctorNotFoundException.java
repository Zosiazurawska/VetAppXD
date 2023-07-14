package pl.kurs.vetapp.exception.doctor;

public class DoctorNotFoundException extends Exception{

    public DoctorNotFoundException(Long id) {
        super("Doctor with ID: " + id + " not found!");
    }
}
