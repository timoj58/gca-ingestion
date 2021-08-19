package com.tabiiki.gca.gcaingestion.router;

import com.tabiiki.gca.gcaingestion.handler.ApiHandler;
import com.tabiiki.gca.gcaingestion.service.ApiService;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class ApiFunction {

    @Bean
    @RouterOperation(beanClass = ApiService.class, beanMethod = "getFinalClaim")
    RouterFunction<ServerResponse> getFinalClaim(ApiHandler apiHandler) {
        return route(RequestPredicates.GET("/final-claim/{id}"), apiHandler::getFinalClaim);
    }

}
