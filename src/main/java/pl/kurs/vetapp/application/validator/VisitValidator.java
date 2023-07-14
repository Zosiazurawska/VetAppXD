package pl.kurs.vetapp.application.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.vetapp.domain.model.Visit;
import pl.kurs.vetapp.exception.client.ClientNotFoundException;
import pl.kurs.vetapp.exception.doctor.DoctorNotFoundException;
import pl.kurs.vetapp.exception.visit.StartDateIsBeforeNowException;
import pl.kurs.vetapp.exception.visit.VisitHaveNotAllParametersException;
import pl.kurs.vetapp.exception.visit.VisitIsNotOnCorrectTimeException;
import pl.kurs.vetapp.exception.visit.VisitTimeIsNotAvailableException;
import pl.kurs.vetapp.repositroy.ClientRepository;
import pl.kurs.vetapp.repositroy.DoctorRepository;
import pl.kurs.vetapp.repositroy.VisitRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VisitValidator {

    private DoctorRepository doctorRepository;
    private ClientRepository clientRepository;
    private final VisitRepository visitRepository;

    public void validate(Visit visit) throws Exception {
        checkDoctorID(visit);
        checkClientID(visit);
        checkIfDateIsNotPast(visit);
        checkIfAllParametersExist(visit);
        checkIfVisitIsOnCorrectTime(visit);
        checkIfVisitIsAvailable(visit);

    }

    private void checkDoctorID(Visit visit) throws DoctorNotFoundException {
        Long doctorId = visit.getDoctor().getId();
        doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException(doctorId));
    }

    private void checkClientID(Visit visit) throws ClientNotFoundException {
        Long clientId = visit.getClient().getId();
        clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    private void checkIfDateIsNotPast(Visit visit) throws StartDateIsBeforeNowException {
        if (visit.getTimeStart().isBefore(LocalDateTime.now())) throw new StartDateIsBeforeNowException();
    }

    private void checkIfVisitIsNotTooLong(Visit visit) throws VisitTimeIsNotAvailableException {
        if (visit.getTimeStart().plusHours(1).isAfter(visit.getTimeEnd())) {
            throw new VisitTimeIsNotAvailableException();
        }
    }

    private void checkIfAllParametersExist(Visit visit) throws VisitHaveNotAllParametersException {
        if (!allParametersExist(visit)) throw new VisitHaveNotAllParametersException();
    }

    private boolean allParametersExist(Visit visit) {
        return visit.getDoctor() != null
                && visit.getClient() != null
                && visit.getTimeStart() != null;
    }

    private void checkIfVisitIsAvailable(Visit visit) throws Exception {
        Long count = visitRepository.findVisitWithSpecificDoctorAndStartTime(visit.getDoctor(), visit.getTimeStart());
        if (count != 0) {
            throw new Exception(new VisitTimeIsNotAvailableException());
        }
    }

    private void checkIfVisitIsOnCorrectTime(Visit visit) throws VisitIsNotOnCorrectTimeException {
        DayOfWeek dayOfWeek = visit.getTimeStart().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || visit.getTimeStart().getHour() < 8 || visit.getTimeStart().getHour() > 17) {
            throw new VisitIsNotOnCorrectTimeException();
        }
    }
}
