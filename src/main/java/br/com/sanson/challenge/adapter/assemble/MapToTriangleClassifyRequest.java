package br.com.sanson.challenge.adapter.assemble;

import br.com.sanson.challenge.adapter.request.TriangleClassifyRequest;

import java.util.Map;

public class MapToTriangleClassifyRequest {

    public static TriangleClassifyRequest assemble(Map request){
        return new TriangleClassifyRequest(
                (Integer) request.get("firstSide"),
                (Integer) request.get("secondSide"),
                (Integer) request.get("thirdSide"));
    }
}
