package br.com.sanson.challenge.application.usecase;

import br.com.sanson.challenge.application.domain.triangle.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTriangleTest {

    public CreateTriangle createTriangle = new CreateTriangle();


    @Test
    public void shouldThrowExceptionForInvalidLengths(){
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> createTriangle.createTriangle(2,3,0));
        assertEquals("All sides have to be the length bigger than 0", thrown.getMessage());
    }

    @Test
    public void shouldCreateEquilateralTriangle(){
        var triangle = createTriangle.createTriangle(2,2,2);
        assertEquals(triangle.getType(), Type.EQUILATERAL);
    }

    @Test
    public void shouldCreateIsoscelesTriangle(){
        var triangle = createTriangle.createTriangle(2,3,2);
        assertEquals(triangle.getType(), Type.ISOSCELES);
    }

    @Test
    public void shouldCreateScaleneTriangle(){
        var triangle = createTriangle.createTriangle(2,3,6);
        assertEquals(triangle.getType(), Type.SCALENE);
    }

}