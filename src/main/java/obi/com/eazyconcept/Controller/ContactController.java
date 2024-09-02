package obi.com.eazyconcept.Controller;

import obi.com.eazyconcept.Entity.Contact;
import obi.com.eazyconcept.Exception.ElementNotFound;
import obi.com.eazyconcept.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ContactController {
   @Autowired
   ContactRepository contactRepository;

    @GetMapping("/admin/addDetails")
    public  String showUpdateDetailsForm(Model model){
        Contact details = new Contact();
        model.addAttribute("details", details);
        return "addContact";
    }

    @PostMapping("/admin/addDetails")
    public String updateDetails(@ModelAttribute("details") Contact details) {

        contactRepository.save(details);
        return "redirect:/admin/viewDetails";
    }
    @GetMapping("/admin/viewDetails")
    public  String viewDetails(Model model){
        Optional<Contact> contact = Optional.ofNullable(contactRepository.findAll().stream().findFirst().orElseThrow(() -> new ElementNotFound("element not found")));

        contact.ifPresent( value -> model.addAttribute("details",value));
        return "view-service-contact-about-us";
    }
    @GetMapping("/admin/details/delete/{id}")
    public String deleteDetails(@PathVariable Long id){
        Optional<Contact> serviceContactAboutUs = contactRepository.findById(id);
       serviceContactAboutUs.ifPresent(contactRepository::delete);
        return "redirect:/admin/viewDetails";
    }
    @GetMapping("/admin/service-contact-about-us/edit{id}")
    public  String UpdateDetailsForm(@PathVariable Long id, Model model){
        Contact contact = contactRepository.findById(id).orElseThrow();
        model.addAttribute("details", contact);
        return  "update-service-contact-about-us";

    }
    @PostMapping("/admin/service-contact-about-us/edit{id}")
    public String UpdateDetails(@ModelAttribute("details") Contact contact, @PathVariable Long id){
        Contact existingDetails = contactRepository.findById(id).orElseThrow();
        existingDetails.setId(contact.getId());
        existingDetails.setEmail(contact.getEmail());
        existingDetails.setFaceBook(contact.getFaceBook());
        existingDetails.setLinkedin(contact.getLinkedin());
        existingDetails.setPhoneNumber(contact.getPhoneNumber());
        contactRepository.save(existingDetails);

        return "redirect:/admin/viewDetails";
    }

}
