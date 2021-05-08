package com.test.message.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSimpleMessage.class)
@JsonDeserialize(as = ImmutableSimpleMessage.class)
public abstract class SimpleMessage {

    public abstract String message();

    public abstract Double version();

    public abstract Long length();
}
