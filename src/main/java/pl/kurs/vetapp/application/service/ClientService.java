package pl.kurs.vetapp.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.kurs.vetapp.application.validator.ClientValidator;
import pl.kurs.vetapp.domain.model.Client;
import pl.kurs.vetapp.exception.client.ClientHaveNotAllParametersException;
import pl.kurs.vetapp.exception.client.ClientNotFoundException;
import pl.kurs.vetapp.exception.doctor.DoctorHaveNotAllParametersException;
import pl.kurs.vetapp.exception.doctor.DuplicationNIPException;
import pl.kurs.vetapp.exception.doctor.SalaryCanNotByNegative;
import pl.kurs.vetapp.repositroy.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;

    public Long createClient(Client newClient) throws ClientHaveNotAllParametersException {
//        clientValidator.validate(newClient);
        Client client = clientRepository.save(newClient);
        return client.getId();
    }

    public void deleteClient(Long id) throws ClientNotFoundException {
        clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.deleteById(id);
    }

    public void editClient(Long id, Client newClient) throws Exception {
        Client oldClient = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        clientValidator.validate(newClient);

        newClient.setFirstname(oldClient.getFirstname());
        newClient.setLastname(oldClient.getLastname());
        newClient.setEmail(oldClient.getEmail());
        newClient.setPetType(oldClient.getPetType());
        newClient.setPetName(oldClient.getPetName());
        newClient.setPetYears(oldClient.getPetYears());

        clientRepository.save(newClient);
    }

    public Client findById(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Page<Client> findAllWithPagination(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public List<Client> findAllWithoutPagination() {
        return clientRepository.findAll();
    }
}
