package obi.com.eazyconcept.Controller;

import jakarta.mail.MessagingException;
import obi.com.eazyconcept.Entity.ContactRequest;
import obi.com.eazyconcept.Service.ContactRequestService;
import obi.com.eazyconcept.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ContactRequestController {
    @Autowired
    ContactRequestService service;

    @Autowired
    EmailService emailService;

    @PostMapping("/send-request")
    public String submitRequest(@RequestParam("email") String email,
                                @RequestParam("subject") String subject,
                                @RequestParam("request") String request,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("phone") String phone){

        ContactRequest newRequest = new ContactRequest();
        newRequest.setRequestEmail(email);
        newRequest.setRequestHeader(subject);
        newRequest.setRequest(request);
        newRequest.setFirstName(firstName);
        newRequest.setLastName(lastName);
        newRequest.setPhoneNumber(phone);
        newRequest.setRequestTime(new Date());
        newRequest.setRead(false);
        service.saveRequest(newRequest);

        try {
            emailService.sentHtmlEmail("obinnadanil@gmail.com",subject,newRequest);
            return "request-success";
        }
        catch (MessagingException e){
            return "failed to send mail"+ e.getMessage();
        }


    }

    @GetMapping("/admin/view-request")
    public String showRequest(Model model){
        List<ContactRequest> requests = service.getRequests().reversed();
        model.addAttribute("unreadCount", service.getUnreadRequestCount());
        model.addAttribute("requests",requests);
        return "view-requests";
    }
    @GetMapping("/admin/view-request/{id}")
    public String viewRequestDetails(@PathVariable Long id, Model model){
        ContactRequest request = service.getRequestById(id);
        request.setRead(true);
        request.setId(id);
        model.addAttribute("requestDetails",request);
        service.saveRequest(request);
        return "requestDetails";

    }
    @GetMapping("/contact-us")
    public  String showContactPage(){
        return "contactRequest";
    }

}
