package agenda.controladores;

import agenda.entidades.Usuario;
import agenda.excepciones.BadRequestException;
import agenda.repositorios.UsuarioRepository;
import agenda.seguridad.Constans;
import agenda.seguridad.JWTAuthenticationConfig;
import agenda.seguridad.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador para el login
@RestController
public class LoginController {

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Recibe usuario y contraseña y devuelve un token si son correctos
    @PostMapping("/login")
    public String login(
            @RequestParam("user") String username,
            @RequestParam("encryptedPass") String encryptedPass)
            throws BadRequestException {

        List<Usuario> usuarios = usuarioRepository.getUsuarios();

        Usuario usuarioEncontrado = null;

        // Buscamos el usuario y comprobamos la contraseña
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) &&
                    PasswordEncryptor.decrypt(usuario.getEncryptedPass()).equals(encryptedPass)) {

                usuarioEncontrado = usuario;
                break;
            }
        }

        // Si no lo encontramos lanzamos error
        if (usuarioEncontrado == null) {
            throw new BadRequestException();
        }

        // Si todo va bien generamos el token
        String token = jwtAuthenticationConfig.getJWTToken(
                usuarioEncontrado.getUsername(),
                usuarioEncontrado.getRol()
        );

        return token;
    }
}
