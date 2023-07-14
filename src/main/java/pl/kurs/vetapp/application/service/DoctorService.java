package pl.kurs.vetapp.application.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.kurs.vetapp.application.validator.DoctorValidator;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.exception.doctor.DoctorNotFoundException;
import pl.kurs.vetapp.repositroy.DoctorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    private DoctorRepository doctorRepository;
    private DoctorValidator doctorValidator;

    public void createDoctor(Doctor doctor) throws Exception {
        doctorValidator.validate(doctor);
        doctorRepository.save(doctor).getId();
    }

    public void deleteDoctor(Long id) throws DoctorNotFoundException {
        doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
        doctorRepository.deleteById(id);
    }

    public void editDoctor(Long id, Doctor newDoctor) throws Exception {
        Doctor oldDoctor = doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
        doctorValidator.validate(newDoctor);

        newDoctor.setFirstname(oldDoctor.getFirstname());
        newDoctor.setLastname(oldDoctor.getLastname());
        newDoctor.setSalary(oldDoctor.getSalary());
        newDoctor.setNip(oldDoctor.getNip());
        newDoctor.setAnimalSpecialization(oldDoctor.getAnimalSpecialization());
        newDoctor.setMedicalSpecialization(oldDoctor.getMedicalSpecialization());
        newDoctor.setHired(oldDoctor.isHired());

        doctorRepository.save(newDoctor);
    }

    public String dismissDoctor(Long id) throws DoctorNotFoundException {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
        doctor.setHired(!doctor.isHired());
        doctorRepository.save(doctor);
        return doctor.isHired() ? "Doctor rehired." : "Doctor dismissed properly.";
    }

    public Doctor findById(Long id) throws DoctorNotFoundException {
        return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
    }

    public Page<Doctor> findAllWithPagination(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    public List<Doctor> findAllWithoutPagination() {
        return doctorRepository.findAll();
    }
}
