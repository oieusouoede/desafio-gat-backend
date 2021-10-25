package ede.desafiogat.repositories;

import ede.desafiogat.domain.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
