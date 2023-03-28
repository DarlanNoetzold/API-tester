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
public class TestPostRequisition extends DefaultRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;

    @Column(name = "is_online", nullable = true)
    private boolean isOnline;

    @Column(name = "gpt_key", nullable = true)
    private String gptKey;

    public TestPostRequisition(Map<String, Object> body, Calendar date_request, List<Result> result, User user) {
        this.body = body.toString();
        this.setDate_request(date_request);
        this.setResult(result);
        this.setUser(user);
    }
}
