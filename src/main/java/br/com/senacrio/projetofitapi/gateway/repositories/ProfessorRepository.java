package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Academia;
import br.com.senacrio.projetofitapi.domain.models.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {
    @Query("select p.academiaFiliada from Professor p where p.id = :id")
    Academia findAcademiaByProfessor(Long id);
}