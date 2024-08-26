package obi.com.eazyconcept.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String requestEmail;
    private  String requestHeader;
    private String phoneNumber;
    private String firstName;
    private  String lastName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private  String request;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  requestTime;
    private  boolean isRead = false;

}
