package ede.desafiogat.repositories;

import ede.desafiogat.domain.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, String> {
}
