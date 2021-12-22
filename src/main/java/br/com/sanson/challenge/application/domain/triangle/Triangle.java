package br.com.sanson.challenge.application.domain.triangle;

import static java.util.Arrays.asList;

public class Triangle {

    private Integer firstSide;
    private Integer secondSide;
    private Integer thirdSide;
    private Type type;

    public Triangle(Integer firstSide, Integer secondSide, Integer thirdSide) {
        for (Integer side: asList(firstSide, secondSide, thirdSide)) {
            this.validate(side);
        }

        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;
        this.type = this.resolverType(firstSide,secondSide,thirdSide);
    }

    private Type resolverType(Integer firstSide,Integer secondSide,Integer thirdSide) {
        if (firstSide == secondSide && secondSide == thirdSide){
            return Type.EQUILATERAL;
        } else if (firstSide == secondSide || firstSide == thirdSide || secondSide == thirdSide){
            return Type.ISOSCELES;
        } else {
            return Type.SCALENE;
        }
    }

    public void validate(Integer value){
        if (value < 1) {
            throw new IllegalArgumentException("All sides have to be the length bigger than 0");
        }
    }
    public Type getType() {
        return this.type;
    }

    public String getTypeAsString() {
        return this.type.toString();
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "firstSide=" + firstSide +
                ", secondSide=" + secondSide +
                ", thirdSide=" + thirdSide +
                ", type=" + type +
                '}';
    }
}
