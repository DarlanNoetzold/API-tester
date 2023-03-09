package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="test_put_requisition")
public class TestPutRequisition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;

    private String url;

    private String headers;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = false)
    private Calendar date_request;

    @ManyToOne
    private User user;
    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;

    @Column(name = "is_online", nullable = true)
    private boolean isOnline;

    @Column(name = "gpt_key", nullable = true)
    private String gptKey;

    public TestPutRequisition(Map<String, Object> body, Calendar date_request, List<Result> result, User user) {
        this.body = body.toString();
        this.date_request = date_request;
        this.result = result;
        this.user = user;
    }
}
