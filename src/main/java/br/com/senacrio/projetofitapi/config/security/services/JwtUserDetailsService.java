package br.com.senacrio.projetofitapi.config.security.services;

import br.com.senacrio.projetofitapi.domain.models.Usuario;
import br.com.senacrio.projetofitapi.gateway.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    public JwtUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = this.repository.findByLogin(login);

        if (user.getLogin().equals(login)) {
            return new User(login, user.getSenha(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
    }

    public UserDetails loadNewUser(Usuario usuario) {
        return new User(usuario.getLogin(), "", new ArrayList<>());
    }
}
