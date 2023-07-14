package pl.kurs.vetapp.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.vetapp.domain.constant.AnimalSpecialization;
import pl.kurs.vetapp.domain.constant.MedicalSpecialization;
import pl.kurs.vetapp.domain.model.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findById(Long id);
    Doctor findByFirstnameAndLastname(String firstname, String lastname);

    List<Doctor> findByMedicalSpecializationAndAnimalSpecialization(MedicalSpecialization medicalSpecialization, AnimalSpecialization animalSpecialization);

}
