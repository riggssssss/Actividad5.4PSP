package agenda.controladores;

import agenda.entidades.Contacto;
import agenda.servicios.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para gestionar los contactos
@RestController
@RequestMapping("/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    @Autowired
    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // Devuelve todos los contactos
    @GetMapping
    public List<Contacto> obtenerTodos() {
        return contactoService.obtenerTodos();
    }

    // Busca un contacto por su id
    @GetMapping("/{id}")
    public Contacto obtenerPorId(@PathVariable Long id) {
        return contactoService.obtenerPorId(id);
    }

    // Guarda un contacto nuevo
    @PostMapping
    public Contacto guardar(@RequestBody Contacto contacto) {
        return contactoService.guardar(contacto);
    }

    // Borra un contacto por su id
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
    }

    // Actualiza un contacto existente
    @PutMapping("/{id}")
    public Contacto actualizar(@PathVariable Long id, @RequestBody Contacto contacto) {
        return contactoService.actualizar(id, contacto);
    }
}
