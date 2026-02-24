package agenda.repositorios;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import agenda.entidades.Rol;
import agenda.entidades.Usuario;
import agenda.seguridad.PasswordEncryptor;


@Repository
public class UsuarioRepository {

    public List<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario(
                "aitor",
                PasswordEncryptor.encrypt("1234"),
                Rol.ADMIN
        ));

        usuarios.add(new Usuario(
                "alicia",
                PasswordEncryptor.encrypt("1111"),
                Rol.USER
        ));

        return usuarios;
    }
}
