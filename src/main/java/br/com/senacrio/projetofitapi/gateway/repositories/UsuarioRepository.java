package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.email = :email")
    Usuario findByEmail(@Param("email") String email);
}
