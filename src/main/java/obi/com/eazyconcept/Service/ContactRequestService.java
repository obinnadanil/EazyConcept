package obi.com.eazyconcept.Service;

import obi.com.eazyconcept.Entity.ContactRequest;
import obi.com.eazyconcept.Repository.ContactRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ContactRequestService {

    @Autowired
    ContactRequestRepo repo;

    public void saveRequest(ContactRequest request){
        repo.save(request);
    }

    public int getUnreadRequestCount(){
        return repo.findAll().stream().filter(request -> !request.isRead()).toList().size();
    }

    public List<ContactRequest> getRequests(){
        return  repo.findAll();
    }

    public ContactRequest getRequestById(Long id){
        return repo.findById(id)
              .orElseThrow(() -> new NoSuchElementException("Request not found"));
    }
}
