package obi.com.eazyconcept.Service;

import jakarta.annotation.PostConstruct;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import obi.com.eazyconcept.Entity.HomePageProjectImage;
import obi.com.eazyconcept.Repository.HomePageImagesRepo;
import obi.com.eazyconcept.Util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class HomePageProjectImagesService {
    @Autowired
    HomePageImagesRepo repo;
    @PostConstruct
    public void init() {
        ImageIO.scanForPlugins();
    }

    @CacheEvict(value = "images", allEntries = true)
    public  void saveHomePageImage(MultipartFile file) throws IOException {
        HomePageProjectImage image = repo.save(HomePageProjectImage.builder()
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .data(file.getBytes()).build());
//        BufferedImage originalImage = ImageIO.read(file.getInputStream());
//        BufferedImage resizedImage = Thumbnails.of(originalImage)
//                .size(800, 600)
//                .outputQuality(1.0)
//                .asBufferedImage();
//        String contentType = file.getContentType();
//        String formatName = null;
//
//        if (contentType != null) {
//            formatName = switch (contentType) {
//                case "image/jpeg" -> "jpg";
//                case "image/png" -> "png";
//                case "image/gif" -> "gif";
//                case "image/bmp" -> "bmp";
//                case "image/webp" -> "webp";
//                default -> throw new IOException("Unsupported image format: " + contentType);
//            };
//        }
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(resizedImage,formatName, outputStream);
//
//        byte[] imageBytes = outputStream.toByteArray();
//
//        image.setName(file.getOriginalFilename());
//        image.setType(file.getContentType());
//        image.setData(imageBytes);
        //repo.save(image);
    }
    @Cacheable("images")
    public  List<HomePageProjectImage> getAllImages(){
        return  repo.findAll();
    }

    public HomePageProjectImage getImageById(Long id){
        return  repo.findById(id).orElseThrow(() -> new NoSuchElementException("not found"));
    }

    public byte[] getImageById2(Long id){
        Optional<HomePageProjectImage> imageOptional = repo.findById(id);
        if(imageOptional.isPresent())
        return ImageUtils.decompressImage(imageOptional.get().getData());
        else throw new RuntimeException("image not found");
    }

    public void deleteImage(HomePageProjectImage image){
        repo.delete(image);
    }
}
