package obi.com.eazyconcept.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageProjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Transient
    private  String base64Data;

    @Lob
    //@Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;


}
