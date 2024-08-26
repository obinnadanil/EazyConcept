package obi.com.eazyconcept.Repository;

import obi.com.eazyconcept.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
