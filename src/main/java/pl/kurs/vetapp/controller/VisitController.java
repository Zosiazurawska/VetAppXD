package pl.kurs.vetapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.vetapp.application.service.VisitService;
import pl.kurs.vetapp.domain.model.DoctorWithAvailableVisits;
import pl.kurs.vetapp.domain.model.Visit;
import pl.kurs.vetapp.domain.model.VisitSearchParameters;
import pl.kurs.vetapp.email.EmailSenderService;
import pl.kurs.vetapp.exception.visit.NoAvailableVisitException;
import pl.kurs.vetapp.exception.visit.VisitIsNotOnCorrectTimeException;

import java.util.List;

@RestController
@RequestMapping("/visit")
@AllArgsConstructor
public class VisitController {

    private static final String VISIT_CREATE_MESSAGE = "Visit created property with ID: ";
    private static final String VISIT_EDIT_MESSAGE = "Visit edited property.";
    private static final String VISIT_DELETE_MESSAGE = "Visit deleted property with ID: ";
    private static final String VISIT_CONFIRM_MESSAGE = "Visit confirmed property.";
    private static final String VISIT_CANCEL_MESSAGE = "Visit canceled property.";
    private VisitService visitService;
    private EmailSenderService emailSenderService;

    @PostMapping("/create")
    public ResponseEntity<String> createVisit(@RequestBody Visit visit) throws Exception {
        Long newVisitID = visitService.createVisit(visit);
        emailSenderService.sendEmail(visit.getClient().getEmail(), visit.getToken());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(VISIT_CREATE_MESSAGE + newVisitID);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editVisit(@RequestBody Visit visit, @PathVariable Long id) throws Exception {
        visitService.editVisit(id, visit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(VISIT_EDIT_MESSAGE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVisit(@PathVariable Long id) throws Exception {
        visitService.deleteVisit(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(VISIT_DELETE_MESSAGE + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitByID(@PathVariable Long id) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visitService.findById(id));
    }

    @GetMapping("/getall")
    // /getall?page=0&size=2
    public ResponseEntity<Page<Visit>> getVisitsWithPagination(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visitService.findAllWithPagination(pageable));
    }

    @GetMapping("")
    public ResponseEntity<List<Visit>> getDoctorsWithoutPagination() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visitService.findAllWithoutPagination());
    }

    @PostMapping("/available")
    public ResponseEntity<List<DoctorWithAvailableVisits>> getAvailableVisits(@RequestBody VisitSearchParameters searchParameters) throws VisitIsNotOnCorrectTimeException, NoAvailableVisitException {
        List<DoctorWithAvailableVisits> availableVisits = visitService.getDoctorsWithAvailableVisits(searchParameters);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(availableVisits);
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<String> confirmVisit(@PathVariable String token) throws Exception {
        visitService.confirmVisit(token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(VISIT_CONFIRM_MESSAGE);
    }

    @GetMapping("/cancel/{token}")
    public ResponseEntity<String> cancelVisit(@PathVariable String token) throws Exception {
        visitService.cancelVisit(token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(VISIT_CANCEL_MESSAGE);
    }
}
