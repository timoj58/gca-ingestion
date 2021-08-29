package com.tabiiki.gca.gcaingestion.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@UtilityClass
public class TransformExceptionHandler {

    public Optional<Object> handle(Consumer<RuntimeException> exceptions, Supplier<Object> transform) {
        try {
            return Optional.of(transform.get());
        } catch (RuntimeException e) {
            exceptions.accept(e);
            return Optional.empty();
        }
    }

}
