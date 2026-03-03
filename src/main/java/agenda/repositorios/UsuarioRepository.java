package agenda.repositorios;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import agenda.entidades.Rol;
import agenda.entidades.Usuario;
import agenda.seguridad.PasswordEncryptor;


// Repositorio para gestionar los usuarios
@Repository
public class UsuarioRepository {

    // Devuelve una lista con usuarios de prueba
    public List<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        // Creamos un admin con clave encriptada
        usuarios.add(new Usuario(
                "aitor",
                PasswordEncryptor.encrypt("1234"),
                Rol.ADMIN
        ));

        // Creamos un usuario normal con clave encriptada
        usuarios.add(new Usuario(
                "alicia",
                PasswordEncryptor.encrypt("1111"),
                Rol.USER
        ));

        return usuarios;
    }
}
