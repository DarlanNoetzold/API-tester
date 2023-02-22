package tech.noetzold.APItester.tests;

import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    public Result fail(TEST_TYPE test_type, String message){
        return new Result(test_type, message);
    }

    public Result success(TEST_TYPE test_type){
        return new Result(test_type, "Success");
    }
}
