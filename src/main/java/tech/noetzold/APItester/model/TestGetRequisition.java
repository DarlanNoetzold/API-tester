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
@Entity(name="test_get_requisition")
public class TestGetRequisition extends DefaultRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String parameters;

    @Column(name = "is_online", nullable = true)
    private boolean isOnline;

    @Column(name = "gpt_key", nullable = true)
    private String gptKey;

    public TestGetRequisition(Map<String, String> parameters, Calendar date_request, List<Result> result, User user) {
        this.parameters = parameters.toString();
        this.setDate_request(date_request);
        this.setResult(result);
        this.setUser(user);
    }
}
