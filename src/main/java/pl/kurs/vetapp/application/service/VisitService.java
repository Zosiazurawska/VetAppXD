package pl.kurs.vetapp.application.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import pl.kurs.vetapp.application.validator.VisitValidator;
import pl.kurs.vetapp.application.validator.VisitValidator;
import pl.kurs.vetapp.domain.constant.AnimalSpecialization;
import pl.kurs.vetapp.domain.constant.MedicalSpecialization;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.domain.model.DoctorWithAvailableVisits;
import pl.kurs.vetapp.domain.model.Visit;
import pl.kurs.vetapp.domain.model.VisitSearchParameters;
import pl.kurs.vetapp.exception.visit.NoAvailableVisitException;
import pl.kurs.vetapp.exception.visit.VisitIsNotOnCorrectTimeException;
import pl.kurs.vetapp.exception.visit.VisitNotFoundException;
import pl.kurs.vetapp.exception.visit.VisitWithTokenNotFound;
import pl.kurs.vetapp.repositroy.DoctorRepository;
import pl.kurs.vetapp.repositroy.VisitRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@AllArgsConstructor
public class VisitService {

    private DoctorRepository doctorRepository;
    private VisitRepository visitRepository;
    private VisitValidator visitValidator;

    public Long createVisit(Visit visit) throws Exception {
        visitValidator.validate(visit);
        visit.setTimeEnd(visit.getTimeStart().plusHours(1));
        visit.setToken(UUID.randomUUID().toString());
        return visitRepository.save(visit).getId();
    }

    public void deleteVisit(Long id) throws VisitNotFoundException {
        visitRepository.findById(id).orElseThrow(() -> new VisitNotFoundException(id));
        visitRepository.deleteById(id);
    }

    public void editVisit(Long id, Visit newVisit) throws Exception {
        Visit oldVisit = visitRepository.findById(id).orElseThrow(() -> new VisitNotFoundException(id));
        visitValidator.validate(newVisit);

        newVisit.setClient(oldVisit.getClient());
        newVisit.setDoctor(oldVisit.getDoctor());
        newVisit.setTimeStart(oldVisit.getTimeStart());
        newVisit.setTimeEnd(oldVisit.getTimeEnd());
        newVisit.setBooked(oldVisit.isBooked());

        visitRepository.save(newVisit);
    }

    public Visit findById(Long id) throws VisitNotFoundException {
        return visitRepository.findById(id).orElseThrow(() -> new VisitNotFoundException(id));
    }

    public Page<Visit> findAllWithPagination(Pageable pageable) {
        return visitRepository.findAll(pageable);
    }

    public List<Visit> findAllWithoutPagination() {
        return visitRepository.findAll();
    }

    public List<DoctorWithAvailableVisits> getDoctorsWithAvailableVisits(VisitSearchParameters visitSearchParameters) throws VisitIsNotOnCorrectTimeException, NoAvailableVisitException {
        LocalDateTime startTime = visitSearchParameters.getStartTime();
        LocalDateTime endTime = visitSearchParameters.getEndTime();
        MedicalSpecialization medicalSpecialization = visitSearchParameters.getMedicalSpecialization();
        AnimalSpecialization animalSpecialization = visitSearchParameters.getAnimalSpecialization();

        validateTime(startTime, endTime);

        List<DoctorWithAvailableVisits> doctorsWithAvailableVisits = new ArrayList<>();

        List<Doctor> doctors = doctorRepository.findByMedicalSpecializationAndAnimalSpecialization(medicalSpecialization, animalSpecialization);

        for (Doctor doctor : doctors) {
            List<String> availableVisits = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            List<Visit> visits = visitRepository.findByDoctorIdAndTimeStartBetween(doctor.getId(), startTime, endTime);

            List<Visit> notBookedVisits = visits.stream().filter(visit -> visit.isBooked()).toList();

            LocalDateTime currentAppointmentTime = startTime.truncatedTo(ChronoUnit.HOURS);

            while (currentAppointmentTime.isBefore(endTime)) {
                if (isWorkingHoursAndDays(currentAppointmentTime)) {
                    if (isTimeCorrect(currentAppointmentTime, notBookedVisits)) {
                        String formattedVisit = currentAppointmentTime.format(formatter);
                        availableVisits.add(formattedVisit);
                    }
                }

                currentAppointmentTime = currentAppointmentTime.plusHours(1);
            }

            DoctorWithAvailableVisits doctorWithAvailableVisits = new DoctorWithAvailableVisits(doctor, availableVisits);
            doctorsWithAvailableVisits.add(doctorWithAvailableVisits);
        }

        isSpecializationsAvailableList(doctorsWithAvailableVisits);

        return doctorsWithAvailableVisits;
    }

    private static void isSpecializationsAvailableList(List<DoctorWithAvailableVisits> visits) throws NoAvailableVisitException {
        if (visits.isEmpty()) throw new NoAvailableVisitException();
    }


    private void validateTime(LocalDateTime startTime, LocalDateTime endTime) throws VisitIsNotOnCorrectTimeException {
        if (startTime.isAfter(endTime) ||
                startTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                startTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)
        ) throw new VisitIsNotOnCorrectTimeException();
    }

    private boolean isWorkingHoursAndDays(LocalDateTime appointmentTime) {
        int hour = appointmentTime.getHour();
        DayOfWeek day = appointmentTime.getDayOfWeek();
        return hour >= 8 &&
                hour < 18 &&
                day != DayOfWeek.SATURDAY &&
                day != DayOfWeek.SUNDAY;
    }

    private boolean isTimeCorrect(LocalDateTime appointmentTime, List<Visit> visits) {
        for (Visit visit : visits) {
            LocalDateTime visitStart = visit.getTimeStart();
            LocalDateTime visitEnd = visit.getTimeEnd();

            if (appointmentTime.isAfter(visitStart.minusHours(1)) && appointmentTime.isBefore(visitEnd)) {
                return false;
            }
        }

        return true;
    }


    public void confirmVisit(String token) throws Exception {
        Long visitId = visitRepository.findByToken(token).orElseThrow(() -> new VisitWithTokenNotFound(token));
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException(visitId));
        visit.setBooked(true);
        visitRepository.save(visit);
    }

    public void cancelVisit(String token) throws Exception {
        Long visitId = visitRepository.findByToken(token).orElseThrow(() -> new VisitWithTokenNotFound(token));
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException(visitId));
        visitRepository.delete(visit);
    }

}
