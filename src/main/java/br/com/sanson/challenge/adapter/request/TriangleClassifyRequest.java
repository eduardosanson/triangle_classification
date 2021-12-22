package br.com.sanson.challenge.adapter.request;

import java.util.Arrays;
import java.util.Map;

public class TriangleClassifyRequest {

    private Integer firstSide;
    private Integer secondSide;
    private Integer thirdSide;

    public TriangleClassifyRequest(Integer firstSide, Integer secondSide, Integer thirdSide) {
        for (Map.Entry<String, Integer> entry:
                Map.of("firstSide", firstSide,"secondSide",secondSide, "thirdSide", thirdSide).entrySet()) {
            validate(entry.getValue(), entry.getKey());
        }
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;
    }

    private void validate(Integer value, String paramName){
        if (value == null || value < 1){
            throw new IllegalArgumentException("parametter " + paramName + " can't be empty or 0");
        }
    }

    public Integer getFirstSide() {
        return firstSide;
    }

    public Integer getSecondSide() {
        return secondSide;
    }

    public Integer getThirdSide() {
        return thirdSide;
    }
}
