package pl.kurs.vetapp.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.vetapp.domain.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
