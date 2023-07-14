package pl.kurs.vetapp.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VISIT")
public class Visit implements Serializable {
    private static final Long serialVersionUID = 1L;

//    @ToString.Include
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ToString.Include
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeStart;
    @ToString.Include
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeEnd;
    private boolean isBooked;
    private String token;

    public boolean isBooked() {
        return isBooked;
    }

    public Visit(Doctor doctorId, Client clientId, LocalDateTime timeStart) {
        this.doctor = doctorId;
        this.client = clientId;
        this.timeStart = timeStart;
        this.timeEnd = timeStart.plusHours(1);
        this.isBooked = false;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Visit visit = (Visit) o;
        return getId() != null && Objects.equals(getId(), visit.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
