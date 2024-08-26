package obi.com.eazyconcept.Service;

import obi.com.eazyconcept.Entity.Admin;
import obi.com.eazyconcept.Entity.AdminUserDetail;
import obi.com.eazyconcept.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUserDetailService implements UserDetailsService {
    @Autowired
    AdminRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = repository.findByUsername(username);
        if(admin.isPresent())
         return new AdminUserDetail(admin.get());
        else  throw new UsernameNotFoundException("User with "+ username+" not found");
    }

    public void saveAdmin(Admin admin){
        repository.save(admin);
    }
}
