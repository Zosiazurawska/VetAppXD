package pl.kurs.vetapp.exception.client;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException(Long id) {
        super("Client with ID: " + id + " not found!");
    }
}
