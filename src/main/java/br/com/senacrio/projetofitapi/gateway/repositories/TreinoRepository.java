package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Aluno;
import br.com.senacrio.projetofitapi.domain.models.Treino;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TreinoRepository extends PagingAndSortingRepository<Treino, Long> {
}
