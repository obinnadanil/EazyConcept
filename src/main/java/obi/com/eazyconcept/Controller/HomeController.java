package obi.com.eazyconcept.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import obi.com.eazyconcept.Entity.HomePageProjectImage;
import obi.com.eazyconcept.Repository.ContactRepository;
import obi.com.eazyconcept.Service.HomePageProjectImagesService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    HomePageProjectImagesService service;
    @Autowired
    ContactRepository contactRepository;


    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<ImageDto> images = service.getAllImages().stream().map(image -> {
            String base64Data = Base64.encodeBase64String(image.getData());
            return new ImageDto(image.getName(), image.getType(), base64Data);
        }).collect(Collectors.toList());
        model.addAttribute("images", images);
        model.addAttribute("details", contactRepository.findAll().getFirst());
        return "homepage";
    }

    @GetMapping("/admin/upload")
    public String showUploadForm(Model model) {
        return "upload";
    }

    @PostMapping("/admin/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            service.saveHomePageImage(file);
            model.addAttribute("message", "Image uploaded successfully");
        } catch (IOException e) {
            model.addAttribute("message", "Error uploading image");
        }
        return "upload";
    }

    @GetMapping("/admin/homePageImages")
    public String getHomePageImages(Model model) {
        List<HomePageProjectImage> images = service.getAllImages();
        for (HomePageProjectImage image : images) {
            String base64Data = Base64.encodeBase64String(image.getData());
            image.setBase64Data(base64Data);
        }
        model.addAttribute("images", images);
        return "homePageImages";
    }

    @GetMapping("/admin/homePageImage/delete/{id}")
    public String deleteHomePageImage(@PathVariable Long id) {
        service.deleteImage(service.getImageById(id));
        return "redirect:/admin/homePageImages";
    }


    @GetMapping("/images")
    public List<HomePageProjectImage> getAllImages() {
        return service.getAllImages();
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        HomePageProjectImage image = service.getImageById(id);

        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getData());
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDto {
        private String name;
        private String type;
        private String data;
    }

}
