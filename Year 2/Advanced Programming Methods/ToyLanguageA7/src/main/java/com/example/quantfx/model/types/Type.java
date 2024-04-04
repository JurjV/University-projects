package com.example.quantfx.model.types;

import com.example.quantfx.model.values.Value;

public interface Type
{
    boolean equals(Object other);
    String toString();
    Value defaultValue();
}
