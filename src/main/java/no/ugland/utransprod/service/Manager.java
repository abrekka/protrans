package no.ugland.utransprod.service;

import java.io.Serializable;

import no.ugland.utransprod.service.enums.LazyLoadEnum;

public interface Manager<E> {
    void lazyLoad(E object,LazyLoadEnum[][] enums);
}
