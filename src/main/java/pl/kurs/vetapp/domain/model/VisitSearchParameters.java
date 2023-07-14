package pl.kurs.vetapp.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import pl.kurs.vetapp.domain.constant.AnimalSpecialization;
import pl.kurs.vetapp.domain.constant.MedicalSpecialization;
import pl.kurs.vetapp.exception.visit.VisitHaveNotAllParametersException;
import pl.kurs.vetapp.exception.visit.VisitIsNotOnCorrectTimeException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VisitSearchParameters {

    @ToString.Include
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startTime;
    @ToString.Include
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endTime;
    private MedicalSpecialization medicalSpecialization;
    private AnimalSpecialization animalSpecialization;


}
