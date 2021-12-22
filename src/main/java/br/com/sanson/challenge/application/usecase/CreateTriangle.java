package br.com.sanson.challenge.application.usecase;

import br.com.sanson.challenge.application.domain.triangle.Triangle;

public class CreateTriangle {

    public Triangle createTriangle(Integer firstSide, Integer secondSide, Integer thirdSide){
        return new Triangle(firstSide, secondSide, thirdSide);
    }
}
