package agenda.repositorios;

import agenda.entidades.Contacto;

import java.util.List;

// Interfaz que define que operaciones se pueden hacer con los contactos
public interface ContactoRepository {
    List<Contacto> obtenerTodos();
    Contacto obtenerPorId(Long id);
    Contacto guardar(Contacto contacto);
    void eliminar(Long id);

    Contacto actualizar(Long id, Contacto contacto);
}
