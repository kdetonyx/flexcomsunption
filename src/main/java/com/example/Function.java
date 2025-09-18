package com.example;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions HTTP Trigger para Flex Consumption
 */
public class Function {
    /**
     * HTTP Trigger que responde con "hola soy un flex"
     * 
     * @param request Solicitud HTTP entrante
     * @param context Contexto de ejecución de la función
     * @return Respuesta HTTP con el mensaje
     */
    @FunctionName("HttpTriggerFlex")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS
            ) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Función HTTP Trigger ejecutándose en Flex Consumption");

        // Responder con el mensaje solicitado
        return request.createResponseBuilder(HttpStatus.OK)
                .header("Content-Type", "text/plain; charset=utf-8")
                .body("hola soy un flex")
                .build();
    }
}