package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Aluno;
import br.com.senacrio.projetofitapi.domain.models.Consultorio;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConsultorioRepository extends PagingAndSortingRepository<Consultorio, Long> {
}
