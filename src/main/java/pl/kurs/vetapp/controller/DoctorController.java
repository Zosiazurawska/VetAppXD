package pl.kurs.vetapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.kurs.vetapp.application.service.DoctorService;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.exception.doctor.DoctorNotFoundException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@AllArgsConstructor

public class DoctorController {
    private static final String DOCTOR_CREATE_MESSAGE = "Doctor created property.";
    private static final String DOCTOR_EDIT_MESSAGE = "Doctor edited property.";
    private static final String DOCTOR_DELETE_MESSAGE = "Doctor deleted property with ID: ";

    private DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor) throws Exception {
        doctorService.createDoctor(doctor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DOCTOR_CREATE_MESSAGE);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editDoctor(@RequestBody Doctor doctor, @PathVariable Long id) throws Exception {
        doctorService.editDoctor(id, doctor);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(DOCTOR_EDIT_MESSAGE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) throws Exception {
        doctorService.deleteDoctor(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(DOCTOR_DELETE_MESSAGE + id);
    }

    @PutMapping("/dismiss/{id}")
    public ResponseEntity<String> dismissDoctor(@PathVariable Long id) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.dismissDoctor(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorByID(@PathVariable Long id) throws DoctorNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findById(id));
    }

    @GetMapping("/getall")
    // /getall?page=0&size=2
    public ResponseEntity<Page<Doctor>> getDoctorsWithPagination(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllWithPagination(pageable));
    }

    @GetMapping("")
    public ResponseEntity<List<Doctor>> getDoctorsWithoutPagination() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllWithoutPagination());
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello!";
    }

}

