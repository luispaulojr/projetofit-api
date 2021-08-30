package br.com.senacrio.projetofitapi.gateway.repositories;

import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.domain.models.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.login = :login and u.status = :status")
    Usuario findByLogin(@Param("login") String login, @Param("status") UserStatus status);

    @Modifying
    @Transactional
    @Query(value = "update Usuario u set u.status = 'ATIVO' where u.login = :login")
    void activeUser(@Param("login") String login);
}
