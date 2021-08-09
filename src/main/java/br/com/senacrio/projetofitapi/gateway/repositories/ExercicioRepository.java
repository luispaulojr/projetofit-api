package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Aluno;
import br.com.senacrio.projetofitapi.domain.models.Exercicio;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExercicioRepository extends PagingAndSortingRepository<Exercicio, Long> {
}
