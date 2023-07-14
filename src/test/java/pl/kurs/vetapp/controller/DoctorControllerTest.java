package pl.kurs.vetapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.vetapp.application.service.DoctorService;
import pl.kurs.vetapp.domain.constant.AnimalSpecialization;
import pl.kurs.vetapp.domain.constant.MedicalSpecialization;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.repositroy.DoctorRepository;
import springfox.documentation.builders.RequestParameterBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorService doctorService;

    private static final String DOCTOR_CREATE_MESSAGE = "Doctor created property.";
    private static final String DOCTOR_EDIT_MESSAGE = "Doctor edited property.";

//    @AfterEach
//    public void cleanup() {
//        doctorRepository.deleteAll();
//    }

    @Test
    public void testCreateDoctor() throws Exception {
        // given
        Doctor doctor = new Doctor();
        doctor.setFirstname("DoctorFirstname");
        doctor.setLastname("DoctorLastname");
        doctor.setMedicalSpecialization(MedicalSpecialization.MEDICAL_SPECIALIZATION_1);
        doctor.setAnimalSpecialization(AnimalSpecialization.DOG);
        doctor.setSalary(35.50);
        doctor.setNip("1234567890");
        doctor.setHired(true);

        String doctorJson = objectMapper.writeValueAsString(doctor);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/doctor/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Doctor createdDoctor = doctorRepository.findByFirstnameAndLastname("DoctorFirstname", "DoctorLastname");

        // then
        assertEquals(DOCTOR_CREATE_MESSAGE, mvcResult.getResponse().getContentAsString());

        assertNotNull(createdDoctor);
        assertEquals("DoctorFirstname", createdDoctor.getFirstname());
        assertEquals("DoctorLastname", createdDoctor.getLastname());
        assertEquals(MedicalSpecialization.MEDICAL_SPECIALIZATION_1, createdDoctor.getMedicalSpecialization());
        assertEquals(AnimalSpecialization.DOG, createdDoctor.getAnimalSpecialization());
        assertEquals(35.50, createdDoctor.getSalary());
        assertEquals("1234567890", createdDoctor.getNip());
        assertTrue(createdDoctor.isHired());
    }

    @Test
    public void testEditDoctor() throws Exception {
        // Utwórz obiekt reprezentujący zmodyfikowanego lekarza
        Doctor doctor = new Doctor();
        doctor.setFirstname("Jan");
        doctor.setLastname("Nowak");
        doctor.setMedicalSpecialization(MedicalSpecialization.MEDICAL_SPECIALIZATION_1);
        doctor.setAnimalSpecialization(AnimalSpecialization.DOG);
        doctor.setSalary(6000.0);
        doctor.setNip("1234567890");
        doctor.setHired(true);

        // Dodaj lekarza do bazy danych
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Konwertuj obiekt na format JSON
        String doctorJson = objectMapper.writeValueAsString(doctor);

        // Wykonaj żądanie PUT na endpoint "/edit/{id}"
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/edit/{id}", savedDoctor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorJson))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Sprawdź odpowiedź
        assertEquals(DOCTOR_EDIT_MESSAGE, mvcResult.getResponse().getContentAsString());

        // Pobierz zaktualizowanego lekarza z bazy danych
        Doctor updatedDoctor = doctorRepository.findById(savedDoctor.getId()).orElse(null);
        assertNotNull(updatedDoctor);

        // Sprawdź, czy wartości atrybutów zostały zaktualizowane
        assertEquals("Jan", updatedDoctor.getFirstname());
        assertEquals("Nowak", updatedDoctor.getLastname());
        assertEquals(MedicalSpecialization.MEDICAL_SPECIALIZATION_1, updatedDoctor.getMedicalSpecialization());
        assertEquals(AnimalSpecialization.DOG, updatedDoctor.getAnimalSpecialization());
        assertEquals(6000.0, updatedDoctor.getSalary());
        assertEquals("1234567890", updatedDoctor.getNip());
        assertTrue(updatedDoctor.isHired());
    }

    @Test
    void deleteDoctor() {
    }

    @Test
    void dismissDoctor() {
    }

    @Test
    void getDoctorByID() throws Exception {

    }

    @Test
    void getDoctorsWithPagination() {
    }

    @Test
    void getDoctorsWithoutPagination() {
    }
}