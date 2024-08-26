package obi.com.eazyconcept.Repository;

import obi.com.eazyconcept.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p from Project p JOIN FETCH p.images WHERE p.id = :id")
    Project findByIdWithImages(@Param("id") Long id);
}
