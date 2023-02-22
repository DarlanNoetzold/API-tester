package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="test_post_requisition")
public class TestPostRequisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    private String url;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = false)
    private Calendar date_request;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;

    public TestPostRequisition(Map<String, String> body, Calendar date_request, List<Result> result) {
        this.body = body.toString();
        this.date_request = date_request;
        this.result = result;
    }
}
