package com.tabiiki.gca.gcaingestion.handler;

import com.tabiiki.gca.gcaingestion.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ApiHandler {

    private final ApiService apiService;

    public Mono<ServerResponse> getFinalClaim(ServerRequest request) {
        return ServerResponse.ok().bodyValue(
                apiService.getFinalClaim(
                        UUID.fromString(request.pathVariable("id")
                        )
                )
        );

    }
}
