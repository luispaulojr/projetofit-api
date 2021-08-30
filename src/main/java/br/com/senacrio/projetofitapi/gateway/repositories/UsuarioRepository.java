package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.login = :login and u.status = 'ATIVO'")
    Usuario findByLogin(@Param("login") String login);
}
