package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    Long ownerId = 1L;
    String lastName = "Smith";
    String firstName = "Mike";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).firstName(firstName).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void deleteById() {
        assertEquals(1L, ownerMapService.findById(ownerId).getId());
        ownerMapService.deleteById(ownerId);
        assertNull(ownerMapService.findById(ownerId));
    }

    @Test
    void delete() {
        assertEquals(1,ownerMapService.findAll().size());
        ownerMapService.deleteById(ownerId);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner2 = Owner.builder().id(id).build();
        Owner savedOwner = ownerMapService.save(owner2);

        assertEquals(id, savedOwner.getId());

    }

    @Test
    void findById() {
        Owner smith = ownerMapService.findById(ownerId);
        assertNotNull(smith);
        assertEquals(ownerId, smith.getId());
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(lastName);
        assertNotNull(smith);
        assertEquals(1L, smith.getId());
    }

    @Test
    void findByFirstName() {
        Owner mike = ownerMapService.findByFirstName("Mike");
        assertEquals(1L, mike.getId());

    }

    @Test
    void findByLastNameNotFound() {
        Owner foo = ownerMapService.findByLastName("foo");
        assertNull(foo);
    }
}