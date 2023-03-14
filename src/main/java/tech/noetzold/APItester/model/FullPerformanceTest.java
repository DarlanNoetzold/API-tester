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

    @Column(name = "test_post_requisition", nullable = true)
    TestPostRequisition testPostRequisition;

    @Column(name = "test_delete_requisition", nullable = true)
    TestDeleteRequisition testDeleteRequisition;

    @Column(name = "test_put_requisition", nullable = true)
    TestPutRequisition testPutRequisition;

    @Column(name = "test_get_requisition", nullable = true)
    TestGetRequisition testGetRequisition;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = false)
    private Calendar date_request;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Result> result;
}
