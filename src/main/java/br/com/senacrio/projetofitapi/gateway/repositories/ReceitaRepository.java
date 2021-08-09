package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import br.com.senacrio.projetofitapi.domain.models.Receita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReceitaRepository extends PagingAndSortingRepository<Receita, Long> {
    @Query("from Nutricionista n where n.id = :id")
    Nutricionista findNutricionistaById(Long id);
}
