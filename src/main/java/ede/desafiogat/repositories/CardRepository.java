package ede.desafiogat.repositories;

import ede.desafiogat.domain.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
