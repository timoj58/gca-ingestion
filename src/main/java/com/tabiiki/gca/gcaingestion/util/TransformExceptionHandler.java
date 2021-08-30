package com.tabiiki.gca.gcaingestion.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@UtilityClass
public class TransformExceptionHandler {

    public Optional<Object> handle(Consumer<RuntimeException> exceptions, Supplier<Object> transform, String key) {
        try {
            return Optional.of(transform.get());
        } catch (RuntimeException e) {
            log.error("error processing {}", key, e);
            exceptions.accept(e);
            return Optional.empty();
        }
    }

}
