package obi.com.eazyconcept.Controller;

import jakarta.transaction.Transactional;
import obi.com.eazyconcept.Entity.Project;
import obi.com.eazyconcept.Entity.ProjectImage;
import obi.com.eazyconcept.Repository.ProjectRepository;
import obi.com.eazyconcept.Service.ProjectImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class ProjectController {
    @Autowired
    ProjectImageService imageService;
    @Autowired
    ProjectRepository projectRepository;


    @GetMapping("/addProject")
    public String showAddProjectForm(){
        return "addProject";
    }


    @PostMapping("/addProject")
    public String addProject(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("mainImage")MultipartFile file,
                             RedirectAttributes redirectAttributes){

        try{
            ProjectImage mainImage =  new ProjectImage();
            mainImage.setData(file.getBytes());
            mainImage.setType(file.getContentType());
            imageService.saveImage(mainImage);

            Project newProject = new Project();
            newProject.setTitle(title);
            newProject.setDescription(description);
            newProject.setMainImage(mainImage);
            newProject.setImages(new ArrayList<>());
            projectRepository.save(newProject);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return "redirect:/admin/projects";
    }

    @GetMapping("/projects/add_pics/{id}")
    public String showAddProjectImageForm(@PathVariable Long id, Model model){
        Optional<Project> projectOptional = projectRepository.findById(id);
        if(projectOptional.isPresent()) {
            model.addAttribute("project", projectOptional.get());
            return "addProjectImage";
        }
        else
            return "redirect:/admin/projects";
    }

    @Transactional
    @PostMapping("/projects/add_pics/{id}")
    public String addProjectPics(@PathVariable Long id,
                                 @RequestParam("images") MultipartFile[] images,
                                 RedirectAttributes attributes){
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            Project existingProject = project.get();
            for(MultipartFile image : images){

                ProjectImage newImage = new ProjectImage();
                try {

                    newImage.setProject(existingProject);
                    newImage.setData(image.getBytes());
                    newImage.setType(image.getContentType());
                    existingProject.getImages().add(newImage);




                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            projectRepository.save(existingProject);
        }
        return "redirect:/admin/projects/" + id ;
    }

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
        return "projects_admin_view";
    }
    @Transactional
    @GetMapping("/projects/{id}")
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
            return "admin_view_project";
        }
        else{
            return "redirect:/projects";
        }

    }
    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id){
        Optional<Project> project = projectRepository.findById(id);
        project.ifPresent(value -> projectRepository.delete(value));
        return "redirect:/admin/projects";

    }

    @GetMapping("/projectImage/delete/{id}")
    public  String deleteProjectImage(@PathVariable Long id){
        Long projectId = imageService.getImageById(id).getProject().getId();
        imageService.deleteImage(imageService.getImageById(id));


        return "redirect:/admin/projects/"+ projectId;

    }


}
