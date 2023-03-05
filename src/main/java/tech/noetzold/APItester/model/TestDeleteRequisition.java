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
@Entity(name="test_delete_requisition")
public class TestDeleteRequisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String parameters;

    private String headers;

    private String url;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = false)
    private Calendar date_request;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;

    private boolean isOnline;

    private String gptKey;

    public TestDeleteRequisition(Map<String, String> parameters, Calendar date_request, List<Result> result, User user) {
        this.parameters = parameters.toString();
        this.date_request = date_request;
        this.result = result;
        this.user = user;
    }
}
