package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NutricionistaRepository extends PagingAndSortingRepository<Nutricionista, Long> {

    @Query("select n.endereco.id from Nutricionista n where n.id = :id")
    Long findIdConsultorioByNutricionista(Long id);
}
