package obi.com.eazyconcept.Service;

import obi.com.eazyconcept.Entity.HomePageProjectImage;
import obi.com.eazyconcept.Repository.HomePageImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HomePageProjectImagesService {
    @Autowired
    HomePageImagesRepo repo;
    public  void saveHomePageImage(MultipartFile file) throws IOException {
        HomePageProjectImage image = new HomePageProjectImage();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        repo.save(image);
    }

    public  List<HomePageProjectImage> getAllImages(){
        return  repo.findAll();
    }

    public HomePageProjectImage getImageById(Long id){
        return  repo.findById(id).orElseThrow(() -> new NoSuchElementException("not found"));
    }

    public void deleteImage(HomePageProjectImage image){
        repo.delete(image);
    }
}
