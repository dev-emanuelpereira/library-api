package io.github.dev_emanuelpereira.libraryapi.security;

import io.github.dev_emanuelpereira.libraryapi.model.Usuario;
import io.github.dev_emanuelpereira.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final static String SENHA_PADRAO = "321";

    private final UsuarioService usuarioService;
    private PasswordEncoder enconder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //Pega o authentication retornado pelo google
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        //transforma em usuario
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Usuario usuario = usuarioService.obterPorEmail(email);

        if (usuario == null) {
            usuario = cadastrarUsuario(email);
        }
        //Transforma a authentication do google na customauthentication que criamos, passando o usuario autenticado
        authentication = new CustomAuthentication(usuario);

        //Seta no contexto Spring o authentication custom
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //passa para frente a authentication
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario cadastrarUsuario(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(enconder.encode(SENHA_PADRAO));
        usuario.setLogin(obterLoginPeloEmail(email));

        return usuario;
    }

    private String obterLoginPeloEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
