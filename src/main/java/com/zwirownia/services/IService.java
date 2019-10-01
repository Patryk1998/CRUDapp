package com.zwirownia.services;

interface IService<T,S> {

    Iterable<T> getAll();
    T getById(Long id);
    void add(S objectDto);
    void update(S objectDto, Long id);
}
