package pl.kurs.vetapp.domain.model;

import lombok.*;

import javax.print.Doc;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DoctorWithAvailableVisits {
    private String firstname;
    private String lastname;
    private List<String> availableVisits;

    public DoctorWithAvailableVisits(Doctor doctor, List<String> availableVisits) {
        this.firstname = doctor.getFirstname();
        this.lastname = doctor.getLastname();
        this.availableVisits = availableVisits;
    }
}
