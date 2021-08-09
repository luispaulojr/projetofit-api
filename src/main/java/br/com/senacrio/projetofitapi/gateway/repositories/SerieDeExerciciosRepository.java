package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Exercicio;
import br.com.senacrio.projetofitapi.domain.models.SerieDeExercicios;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SerieDeExerciciosRepository extends PagingAndSortingRepository<SerieDeExercicios, Long> {
    @Query("select s.exercicios from SerieDeExercicios s where s.id = :id")
    List<Exercicio> findExerciciosBySerie(Long id);
}