package obi.com.eazyconcept.Service;

import obi.com.eazyconcept.Repository.ProjectImageRepository;
import obi.com.eazyconcept.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectImageRepository imageRepository;


}
