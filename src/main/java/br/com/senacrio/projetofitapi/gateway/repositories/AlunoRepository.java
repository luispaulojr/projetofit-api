package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Aluno;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AlunoRepository extends PagingAndSortingRepository<Aluno, Long> {
    Optional<Aluno> findByLogin(String login);
}
