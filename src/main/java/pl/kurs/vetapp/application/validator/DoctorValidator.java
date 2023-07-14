package pl.kurs.vetapp.application.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.exception.doctor.DoctorHaveNotAllParametersException;
import pl.kurs.vetapp.exception.doctor.DuplicationNIPException;
import pl.kurs.vetapp.exception.doctor.SalaryCanNotByNegative;
import pl.kurs.vetapp.repositroy.DoctorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorValidator {
    private DoctorRepository doctorRepository;

    public void validate(Doctor doctor) throws Exception {
        checkIfAllParametersExist(doctor);
        checkIfSalaryIsNotNegative(doctor);
        checkIfNIPIsNotDuplicated(doctor);
    }

    private void checkIfAllParametersExist(Doctor doctor) throws DoctorHaveNotAllParametersException {
        if (!allParametersExist(doctor)) throw new DoctorHaveNotAllParametersException();
    }

    private void checkIfSalaryIsNotNegative(Doctor doctor) throws SalaryCanNotByNegative {
        if (doctor.getSalary() <= 0) throw new SalaryCanNotByNegative();
    }
    private void checkIfNIPIsNotDuplicated(Doctor doctor) throws DuplicationNIPException {
        if (getAllNIPs().contains(doctor.getNip())) throw new DuplicationNIPException(doctor.getNip());
    }

    private boolean allParametersExist(Doctor doctor) {
        return     !doctor.getFirstname().isEmpty()
                && !doctor.getLastname().isEmpty()
                && !doctor.getNip().isEmpty()
                && doctor.getAnimalSpecialization().describeConstable().isPresent()
                && doctor.getMedicalSpecialization().describeConstable().isPresent();
    }

    private List<String> getAllNIPs(){
        return doctorRepository.findAll().stream()
                .map(Doctor::getNip)
                .toList();
    }
}
