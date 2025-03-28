package com.semenihin.validator;

public interface Validator <T> {
    boolean validate(T Object);

    boolean exist(T Object);

    boolean exist(long objectId);
}
