package br.com.sanson.challenge.adapter;

import br.com.sanson.challenge.adapter.request.TriangleClassifyRequest;
import br.com.sanson.challenge.application.domain.triangle.Type;
import br.com.sanson.challenge.application.usecase.CreateTriangle;

import java.util.Map;

public class MapEventAdapter {

    private CreateTriangle createTriangle;

    public MapEventAdapter(CreateTriangle createTriangle) {
        this.createTriangle = createTriangle;
    }

    public Map<String, String> createTriangle(TriangleClassifyRequest request){
        var triangle = createTriangle.createTriangle(request.getFirstSide(), request.getSecondSide(), request.getThirdSide());
        var response = Map.of("type", triangle.getTypeAsString());
        return response;
    }
}
