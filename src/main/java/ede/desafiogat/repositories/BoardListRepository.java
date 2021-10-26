package ede.desafiogat.repositories;

import ede.desafiogat.domain.models.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardListRepository extends JpaRepository<BoardList, String> {
}
