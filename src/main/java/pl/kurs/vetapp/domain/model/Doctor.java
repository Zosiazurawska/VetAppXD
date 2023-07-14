package pl.kurs.vetapp.domain.model;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import pl.kurs.vetapp.domain.constant.AnimalSpecialization;
import pl.kurs.vetapp.domain.constant.MedicalSpecialization;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DOCTOR")
public class Doctor implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    @Column(name = "medical_specialization")
    private MedicalSpecialization medicalSpecialization;
    @Enumerated(EnumType.STRING)
    @Column(name = "animal_specialization")
    private AnimalSpecialization animalSpecialization;
    private Double salary;
    private String nip;
    private boolean isHired;

    public Doctor(String firstname, String lastname, MedicalSpecialization medicalSpecialization, AnimalSpecialization animalSpecialization, Double salary, String nip, boolean isHired) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.medicalSpecialization = medicalSpecialization;
        this.animalSpecialization = animalSpecialization;
        this.salary = salary;
        this.nip = nip;
        this.isHired = isHired;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Doctor doctor = (Doctor) o;
        return getId() != null && Objects.equals(getId(), doctor.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
