package com.tabiiki.gca.gcaingestion.handler;

import com.tabiiki.gca.gcaingestion.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ApiHandler {

    private final ApiService apiService;

    public Mono<ServerResponse> objectSummaries(ServerRequest request) {
        return ServerResponse.ok().bodyValue(apiService.objectSummaries(
                request.queryParam("folder").get()
        ));
    }

}
