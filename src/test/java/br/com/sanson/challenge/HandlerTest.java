package br.com.sanson.challenge;

import br.com.sanson.challenge.adapter.MapEventAdapter;
import br.com.sanson.challenge.application.domain.triangle.Type;
import br.com.sanson.challenge.infrastructure.dao.RequestRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class HandlerTest {


    @Mock
    Context context;

    @Mock
    LambdaLogger logger;

    @Mock
    MapEventAdapter adapter;

    @Mock
    RequestRepository requestRepository;

    static Handler handler;

    @BeforeEach
    void init() {
        handler = new Handler(adapter, requestRepository);
    }

    @Test
    void givenValidRequest_whenCalculateSides_thenReturnTriangleType() {

        when(context.getLogger()).thenReturn(logger);

        when(adapter.createTriangle(any())).thenReturn(Map.of("type", Type.EQUILATERAL.toString()));

        Map<String, Object> event = new HashMap<>();

        event.put("firstSide",1);
        event.put("secondSide",1);
        event.put("thirdSide",1);

        var response = handler.handleRequest(event, context);

        assertNotNull(response);
        assertEquals(response.get("type"), Type.EQUILATERAL.toString());
    }

    @Test
    void givenInvalildRequest_whenCalculateSides_thenReturnBadRequest() {

        when(context.getLogger()).thenReturn(logger);

        when(adapter.createTriangle(any())).thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        Map<String, Object> event = new HashMap<>();

        event.put("firstSide",1);
        event.put("secondSide",1);
        event.put("thirdSide",1);

        var response = handler.handleRequest(event, context);

        assertNotNull(response);
        assertEquals("IllegalArgumentException", response.get("message"));
        assertEquals(400, response.get("statusCode"));
    }

    @Test
    void givenARequest_whenSystemWasTrouble_thenReturnInternalError() {

        when(context.getLogger()).thenReturn(logger);

        when(adapter.createTriangle(any())).thenThrow(new RuntimeException("InternalError"));

        Map<String, Object> event = new HashMap<>();

        event.put("firstSide",1);
        event.put("secondSide",1);
        event.put("thirdSide",1);

        var response = handler.handleRequest(event, context);

        assertNotNull(response);
        assertEquals("InternalError", response.get("message"));
        assertEquals(500, response.get("statusCode"));
    }

}