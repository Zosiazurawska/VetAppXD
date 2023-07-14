package pl.kurs.vetapp.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import pl.kurs.vetapp.domain.constant.AnimalType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENT")
public class Client implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String petName;
    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType petType;
    private Integer petYears;
    private String email;

    public Client(String firstname, String lastname, String petName, AnimalType petType, Integer petYears, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.petName = petName;
        this.petType = petType;
        this.petYears = petYears;
        this.email = email;
    }
}
