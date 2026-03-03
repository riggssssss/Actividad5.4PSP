package agenda.repositorios;

import agenda.entidades.Contacto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Implementacion real del repositorio de contactos
@Repository
public class ContactoRepositoryImpl implements ContactoRepository {
    // Usamos un mapa en memoria para guardar los contactos
    private final Map<Long, Contacto> contactos = new HashMap<>();
    private Long idSecuencia = 1L;

    // Constructor que mete un contacto de prueba al arrancar
    public ContactoRepositoryImpl() {
        Contacto contactoInicial = new Contacto("Aitor", "123456789");
        contactoInicial.setId(idSecuencia++);
        contactos.put(contactoInicial.getId(), contactoInicial);
    }

    // Devuelve todos los contactos
    @Override
    public List<Contacto> obtenerTodos() {
        return new ArrayList<>(contactos.values());
    }

    // Busca un contacto por su ID
    @Override
    public Contacto obtenerPorId(Long id) {
        return contactos.get(id);
    }

    // Guarda un contacto nuevo asignandole un ID
    @Override
    public Contacto guardar(Contacto contacto) {
        if (contacto.getId() == null) {
            contacto.setId(idSecuencia++);
        }
        contactos.put(contacto.getId(), contacto);
        return contacto;
    }

    // Borra un contacto del mapa
    @Override
    public void eliminar(Long id) {
        contactos.remove(id);
    }

    // Actualiza los datos de un contacto existente
    @Override
    public Contacto actualizar(Long id, Contacto contacto) {
        if (!contactos.containsKey(id)) {
            return null;
        }

        contacto.setId(id);
        contactos.put(id, contacto);
        return contacto;
    }
}
