package com.github.osndok.pulp.cli.wui.model;

import org.apache.tapestry5.beaneditor.RelativePosition;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.beanmodel.PropertyConduit;
import org.apache.tapestry5.beanmodel.PropertyModel;
import org.apache.tapestry5.beanmodel.internal.beanmodel.PropertyModelImpl;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.json.JSONObject;

import java.util.List;

public
class JsonBeanModel implements BeanModel<JSONObject>
{
    private final
    List<String> propertyNames;

    private final
    Messages messages;

    public
    JsonBeanModel(final List<String> propertyNames, final Messages messages)
    {
        if (messages == null) throw new NullPointerException("messages");

        this.propertyNames = propertyNames;
        this.messages = messages;
    }

    @Override
    public
    Class<JSONObject> getBeanType()
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    JSONObject newInstance()
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    List<String> getPropertyNames()
    {
        return propertyNames;
    }

    @Override
    public
    PropertyModel get(final String fieldName)
    {
        var conduit = new JsonPropertyConduit(fieldName);
        return new PropertyModelImpl(this, fieldName, conduit, messages);
    }

    @Override
    public
    PropertyModel getById(final String fieldName)
    {
        // ???: Tapestry calls this method when sorting by a specific field.
        return get(fieldName);
    }

    @Override
    public
    PropertyModel add(final String s)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    PropertyModel addExpression(final String s, final String s1)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    PropertyModel addEmpty(final String s)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    PropertyModel add(final RelativePosition relativePosition, final String s, final String s1)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    PropertyModel add(
            final RelativePosition relativePosition,
            final String s,
            final String s1,
            final PropertyConduit propertyConduit
    )
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    PropertyModel add(final String s, final PropertyConduit propertyConduit)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    BeanModel<JSONObject> exclude(final String... strings)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    BeanModel<JSONObject> reorder(final String... strings)
    {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public
    BeanModel<JSONObject> include(final String... strings)
    {
        throw new UnsupportedOperationException("unimplemented");
    }
}
