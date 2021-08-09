package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Receita;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReceitaRepository extends PagingAndSortingRepository<Receita, Long> {
}
