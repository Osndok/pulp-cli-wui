package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;
import com.github.osndok.pulp.cli.wui.services.ColorPatternService;
import com.github.osndok.pulp.cli.wui.services.HelpDocsService;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.beanmodel.services.BeanModelSource;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.commons.services.PropertyAccess;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import java.util.Arrays;

public abstract
class BasicFormBasedExecution
{
    public abstract Object getCommandObject();
    public abstract Class getNextPage();

    @Inject
    private
    Logger log;

    @Property
    private
    HelpDocs helpDocs;

    @Property
    private
    String topName;

    @Property
    private
    BeanModel beanModel;

    private
    String[] subCommandChain;

    @Inject
    private
    HelpDocsService helpDocsService;

    @Inject
    private
    PropertyAccess propertyAccess;

    @Inject
    private
    ComponentResources componentResources;

    @Inject
    private
    BeanModelSource beanModelSource;

    @Inject
    private
    ColorPatternService colorPatternService;

    public final
    Object onActivate()
    {
        // e.g. pageName = "pulp/rpm/remote/Create"
        String[] segments = componentResources.getPageName().split("/");
        subCommandChain = Arrays.stream(segments, 1, segments.length)
                .map(String::toLowerCase)
                .toArray(String[]::new);
        topName = subCommandChain[subCommandChain.length-1];

        log.debug("onActivate(): object: {} ", getCommandObject());

        var commandObject = getCommandObject();
        helpDocs = helpDocsService.getHelpDocsFor(subCommandChain);

        // Any way to get our sub-page's messages, instead of ours?
        var messages = componentResources.getMessages();

        // Ordinarily, the beanEditForm would automatically pull the class from the binding
        // channel, but since we have it as a generic Object here, we have to "manually"
        // get the model from the object's class at runtime.
        beanModel = beanModelSource.createEditModel(commandObject.getClass(), messages);

        return null;
    }

    public
    String getPrimaryBgColor()
    {
        return colorPatternService.getCssColorFor(topName);
    }

    public final
    Object onSuccess()
    {
        log.debug("onSuccess(): {}", componentResources.getPageName());

        var commandObject = getCommandObject();
        log.info("onSuccess() w/ subCommandChain: {} & commandObject:{}", subCommandChain, commandObject);
        var adapter = propertyAccess.getAdapter(commandObject);
        for (String propertyName : adapter.getPropertyNames())
        {
            var value = adapter.get(commandObject, propertyName);
            // Intent is to transform "some_thing" into "--some-thing"
            var transformed = "--" + propertyName.toString().replace("_", "-");
            log.debug("{}: {} {}", propertyName, transformed, value);
        }

        return getNextPage();
    }
}
