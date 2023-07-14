package pl.kurs.vetapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.vetapp.application.service.ClientService;
import pl.kurs.vetapp.domain.model.Client;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.exception.client.ClientNotFoundException;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/client")

@AllArgsConstructor
public class ClientController {
    private static final String CLIENT_CREATE_MESSAGE = "Client created property with ID: ";
    private static final String CLIENT_EDIT_MESSAGE = "Client edited property";
    private static final String CLIENT_DELETE_MESSAGE = "Client deleted property with ID: ";

    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@RequestBody Client client) throws Exception {
        Long newClientID = clientService.createClient(client);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CLIENT_CREATE_MESSAGE + newClientID);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editClient(@RequestBody Client client, @PathVariable Long id) throws Exception {
        clientService.editClient(id, client);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CLIENT_EDIT_MESSAGE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) throws ClientNotFoundException {
        clientService.deleteClient(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CLIENT_DELETE_MESSAGE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientByID(@PathVariable Long id) throws ClientNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.findById(id));
    }

    @GetMapping("/getall")
    // /getall?page=0&size=2
    public ResponseEntity<Page<Client>> getDoctorsWithPagination(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.findAllWithPagination(pageable));
    }

    @GetMapping("")
    public ResponseEntity<List<Client>> getDoctorsWithoutPagination() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.findAllWithoutPagination());
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello!";
    }

}

