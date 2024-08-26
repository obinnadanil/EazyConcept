package obi.com.eazyconcept.Controller;

import obi.com.eazyconcept.Entity.Admin;
import obi.com.eazyconcept.Service.AdminUserDetailService;
import obi.com.eazyconcept.Service.ContactRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
 @Autowired
    AdminUserDetailService service;

 @Autowired
    PasswordEncoder encoder;
    @Autowired
    ContactRequestService requestService;

    @GetMapping("/admin/addAdmin")
    public String showAddAdminForm(Model model) {
        Admin admin = new Admin();
        model.addAttribute("admin", admin);
        return "addAdmin";
    }

    @PostMapping("/admin/addAdmin")
    public String addAdmin(
            @ModelAttribute("admin") Admin admin) {



        Admin newAdmin =  new Admin();
        newAdmin.setUsername(admin.getUsername());
        newAdmin.setPassword(encoder.encode(admin.getPassword()));
        service.saveAdmin(newAdmin);

        return "redirect:/admin/adminPortal"; // Redirect to a success page or another page
    }

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/admin/adminPortal")
    public  String showAdminPortal(Model model){
        model.addAttribute("unreadCount", requestService.getUnreadRequestCount());
        return "AdminPortal";
    }
}
