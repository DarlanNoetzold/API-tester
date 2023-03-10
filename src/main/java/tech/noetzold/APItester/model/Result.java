package tech.noetzold.APItester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.noetzold.APItester.util.TEST_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Result")
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private TEST_TYPE test_type;

    @NotNull
    @Lob
    private String details;



    public Result(TEST_TYPE test_type, String message) {
        this.test_type = test_type;
        this.details = message;
    }
}
