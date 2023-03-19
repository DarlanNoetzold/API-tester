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
@Entity(name="test_performance")
public class FullPerformanceTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parameters", nullable = true)
    private String parameters;

    @Column(name = "headers", nullable = true)
    private String headers;

    @Column(name = "url", nullable = true)
    private String url;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = false)
    private Calendar date_request;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;

    @Column(name = "body", nullable = true)
    private String body;

    @Column(name = "method", nullable = true)
    private String method;

    @Column(name = "num_req", nullable = true)
    private int numReq;
}
