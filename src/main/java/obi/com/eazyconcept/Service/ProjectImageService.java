package obi.com.eazyconcept.Service;

import obi.com.eazyconcept.Entity.ProjectImage;
import obi.com.eazyconcept.Repository.ProjectImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProjectImageService {

    @Autowired
    ProjectImageRepository imageRepository;

    public void saveImage(ProjectImage image){
       imageRepository.save(image);
    }
    public  void deleteImage(ProjectImage image){
        imageRepository.delete(image);
    }

    public ProjectImage getImageById(Long id){
        return imageRepository.findById(id).
        orElseThrow(() -> new NoSuchElementException("Image Not found"));
    }
}
