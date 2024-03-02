package ru.itmo.hsauth.service.util;

public interface Mapper<T, E> {
    E entityToDto(T entity);
    T dtoToEntity(E dto);
}
