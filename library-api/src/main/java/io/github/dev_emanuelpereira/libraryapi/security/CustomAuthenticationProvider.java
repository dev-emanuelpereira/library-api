package io.github.dev_emanuelpereira.libraryapi.security;

import io.github.dev_emanuelpereira.libraryapi.model.Usuario;
import io.github.dev_emanuelpereira.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitado = authentication.getCredentials().toString();

        Usuario usuario = usuarioService.obterPorLogin(login);

        if (usuario == null) {
            throw getUsernameNotFoundException();
        }

        String senhaCriptografada = usuario.getSenha();

        boolean senhasBatem = encoder.matches(senhaDigitado, senhaCriptografada);

        if(senhasBatem) {
            return new CustomAuthentication(usuario);
        }

        if (usuario == null) {
            throw getUsernameNotFoundException();
        }

        return null;
    }

    private UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("Usuario ou senha incorretos.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
