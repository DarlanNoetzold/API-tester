package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="test_get_requisition")
public class TestGetRequisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String parameters;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = false)
    private Calendar date_request;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;

    public TestGetRequisition(Map<String, String> parameters, Calendar date_request, List<Result> result, User user) {
        this.parameters = parameters.toString();
        this.date_request = date_request;
        this.result = result;
        this.user = user;
    }
}
