package pl.kurs.vetapp.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.domain.model.Visit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT COUNT(v) FROM Visit v WHERE " +
            "v.doctor = :doctor " +
            "AND v.isBooked = true " +
            "AND v.timeStart BETWEEN DATEADD(HOUR, -1, :startTime) " +
            "AND DATEADD(HOUR, 1, :startTime) ")
    Long findVisitWithSpecificDoctorAndStartTime(@Param("doctor") Doctor doctor,
                                                 @Param("startTime") LocalDateTime startTime);

    @Query("SELECT v.id FROM Visit v WHERE " +
            "v.token = :token")
    Optional<Long> findByToken(@Param("token") String token);


    List<Visit> findByDoctorIdAndTimeStartBetween(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);

    List<Visit> findByDoctorIdAndTimeStart(Long id, LocalDateTime appointmentTime);

//    @Query("SELECT v FROM Visit v " +
//            "JOIN v.doctor d " +
//            "WHERE " +
//            "d.id = :doctorId " +
//            "AND d.animalSpecialization = :animalType " +
//            "AND d.medicalSpecialization = :medicalSpecialization " +
//            "AND v.isBooked = true " +
//            "AND v.timeStart BETWEEN :timeStart AND DATEADD('MONTH', 1, :timeStart)")
//    List<Visit> searchVisit(@Param("doctorId") Long doctorId,
//                            @Param("animalType") AnimalType animalType,
//                            @Param("medicalSpecialization") String medicalSpecialization,
//                            @Param("timeStart") LocalDate timeStart);

}
