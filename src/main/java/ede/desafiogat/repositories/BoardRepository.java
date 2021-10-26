package ede.desafiogat.repositories;

import ede.desafiogat.domain.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository <Board, String> {
}
