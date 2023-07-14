package pl.kurs.vetapp.exception.client;

public class ClientHaveNotAllParametersException extends Exception{
    public ClientHaveNotAllParametersException() {
        super("Parameters can not be empty.");
    }
}
