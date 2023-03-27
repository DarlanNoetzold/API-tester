package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="complete_request")
public class CompleteRequest extends DefaultRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parameters", nullable = true)
    private String parameters;

    @Column(name = "body", nullable = true)
    private String body;

    @Column(name = "method", nullable = true)
    private String method;

}
