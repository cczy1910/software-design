package ru.akirakozov.sd.refactoring.util;

@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R apply(T arg) throws Exception;
}
