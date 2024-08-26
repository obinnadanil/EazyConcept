package obi.com.eazyconcept.Repository;

import obi.com.eazyconcept.Entity.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRequestRepo extends JpaRepository<ContactRequest, Long>{

}
