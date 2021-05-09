package com.test.message.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableComplexMessage.class)
@JsonDeserialize(as = ImmutableComplexMessage.class)
public abstract class ComplexMessage {

    public abstract String message();

    public abstract Double version();

    public abstract Long length();

    public abstract SimpleMessage simpleMessage();
}
