package com.gearglobe.app.backend.offer.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OfferCreationStrategyRegistry {
    private final Map<Class<?>, OfferCreationStrategy<?>> strategyMap;

    public OfferCreationStrategyRegistry(List<OfferCreationStrategy<?>> strategies) {
        this.strategyMap = strategies.stream()
            .collect(Collectors.toMap(OfferCreationStrategy::getSupportedType, Function.identity()));
    }

    public OfferCreationStrategy<?> getStrategy(Class<?> dtoClass) {
        OfferCreationStrategy<?> strategy = strategyMap.get(dtoClass);
        if (strategy == null) {
            throw new IllegalArgumentException("Offer type not found: " + dtoClass);
        }
        return strategy;
    }
}
