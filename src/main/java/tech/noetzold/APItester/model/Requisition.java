package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.noetzold.APItester.util.REQ_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Requisition")
public class Requisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    private String parameters;

    @NotNull
    private REQ_TYPE req_type;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro", nullable = false)
    private Calendar date_request;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;

    public Requisition(Map<String, String> parameters, REQ_TYPE req_type, Calendar date_request, List<Result> result) {
        this.parameters = parameters.toString();
        this.req_type = req_type;
        this.date_request = date_request;
        this.result = result;
    }
}
