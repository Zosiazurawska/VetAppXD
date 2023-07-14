package pl.kurs.vetapp.application.validator;

import org.springframework.stereotype.Service;
import pl.kurs.vetapp.domain.model.Client;
import pl.kurs.vetapp.exception.client.ClientHaveNotAllParametersException;
import pl.kurs.vetapp.exception.doctor.DoctorHaveNotAllParametersException;
import pl.kurs.vetapp.exception.doctor.DuplicationNIPException;
import pl.kurs.vetapp.exception.doctor.SalaryCanNotByNegative;

@Service
public class ClientValidator {

    public void validate(Client client) throws ClientHaveNotAllParametersException {
        checkIfAllParametersExist(client);
    }

    private void checkIfAllParametersExist(Client client) throws ClientHaveNotAllParametersException {
        if (!allParametersExist(client)) throw new ClientHaveNotAllParametersException();
    }

    private boolean allParametersExist(Client client) {
        return     client.getFirstname().describeConstable().isEmpty()
                && !client.getLastname().isEmpty()
                && !client.getPetName().isEmpty()
                && client.getPetYears().describeConstable().isPresent()
                && client.getPetType().describeConstable().isPresent()
                && !client.getEmail().isEmpty();
    }

}
