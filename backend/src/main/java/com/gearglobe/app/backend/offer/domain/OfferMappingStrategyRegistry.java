package com.gearglobe.app.backend.offer.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OfferMappingStrategyRegistry {
    private final Map<Class<?>, OfferMappingStrategy<?, ?>> strategyMap;

    public OfferMappingStrategyRegistry(List<OfferMappingStrategy<?, ?>> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(OfferMappingStrategy::getSupportedType, Function.identity()));
    }

    public OfferMappingStrategy<?, ?> getStrategy(Class<?> entityClass) {
        OfferMappingStrategy<?, ?> strategy = strategyMap.get(entityClass);
        if (strategy == null) {
            throw new IllegalArgumentException("Offer type not found: " + entityClass);
        }
        return strategy;
    }
}
