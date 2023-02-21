package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.noetzold.APItester.util.REQ_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Requisition")
public class Requisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Map<String, Object> body;

    private Map<String, Object> parameters;

    @NotNull
    private REQ_TYPE req_type;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro", nullable = false)
    private Calendar date_request;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Result result;
}
