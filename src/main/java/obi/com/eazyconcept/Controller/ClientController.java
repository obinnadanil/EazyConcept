package obi.com.eazyconcept.Controller;

import jakarta.transaction.Transactional;
import obi.com.eazyconcept.Entity.Project;
import obi.com.eazyconcept.Entity.ProjectImage;
import obi.com.eazyconcept.Repository.ProjectRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/projects")
    public String showAllProject(Model model){
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
            if (project.getMainImage() != null) {
                byte[] imageData = project.getMainImage().getData();
                String base64Image = Base64.encodeBase64String(imageData);
                project.getMainImage().setBase64Data(base64Image);
            }
        }
        model.addAttribute("projects", projects);
        return "projects";
    }
    @Transactional
    @GetMapping("/projects/view/{id}")
    public  String viewProject(@PathVariable Long id, Model model){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            byte[] mainImageData = project.getMainImage().getData();
            String mainBase64Image = Base64.encodeBase64String(mainImageData);
            project.getMainImage().setBase64Data(mainBase64Image);
            List<ProjectImage> images = project.getImages();
            for(ProjectImage image : images){
                byte[] imageData = image.getData();
                String base64String = Base64.encodeBase64String(imageData);
                image.setBase64Data(base64String);
            }
            model.addAttribute("project", project);
            return "client_view_project";
        }
        else{
            return "redirect:/projects";
        }

    }
}
