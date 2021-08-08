package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Endereco;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NutricionistaRepository extends PagingAndSortingRepository<Nutricionista, Long> {

    @Query("from Endereco e inner join fetch e.nutricionista where e.nutricionista.id = :id")
    Endereco findByConsultorioId(Long id);
}
