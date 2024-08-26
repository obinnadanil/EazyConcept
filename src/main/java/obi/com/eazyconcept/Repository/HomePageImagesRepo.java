package obi.com.eazyconcept.Repository;

import obi.com.eazyconcept.Entity.HomePageProjectImage;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface HomePageImagesRepo extends JpaRepository<HomePageProjectImage,Long> {

}
