package com.github.osndok.pulp.cli.wui.model;

import org.apache.tapestry5.beanmodel.PropertyConduit;
import org.apache.tapestry5.json.JSONObject;

import java.lang.annotation.Annotation;

public
class JsonPropertyConduit implements PropertyConduit
{
    private final String fieldName;

    public
    JsonPropertyConduit(final String fieldName)
    {
        this.fieldName = fieldName;
    }

    @Override
    public
    Object get(final Object o)
    {
        return ((JSONObject)o).get(fieldName);
    }

    @Override
    public
    void set(final Object o, final Object o1)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    Class getPropertyType()
    {
        return String.class;
    }

    @Override
    public
    <T extends Annotation>
    T getAnnotation(final Class<T> aClass)
    {
        return null;
    }
}
