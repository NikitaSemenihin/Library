package com.semenihin.validator;

public interface ValidatorInterface<T> {
    boolean validate(T object);
}
