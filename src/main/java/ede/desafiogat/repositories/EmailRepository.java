package ede.desafiogat.repositories;

import ede.desafiogat.domain.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, String> {
}
