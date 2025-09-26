package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;
import com.github.osndok.pulp.cli.wui.services.ColorPatternService;
import com.github.osndok.pulp.cli.wui.services.HelpDocsService;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.beanmodel.services.BeanModelSource;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.commons.services.PropertyAccess;
import org.apache.tapestry5.http.services.Response;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract
class BasicFormBasedExecution
{
    public abstract Object getCommandObject();

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
    Object onSuccess() throws Exception
    {
        log.debug("onSuccess(): {}", subCommandChain);

        var outputStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        var out = new PrintWriter(writer);

        out.print("Getting ready to execute:\npulp");
        var procBuilder = new ProcBuilder("pulp")
                // TODO: We should capture the stdout separately, and parse or beautify the json.
                .withOutputStream(outputStream)
                .withErrorStream(outputStream)
                .withArgs(subCommandChain);

        for (String s : subCommandChain)
        {
            out.print(' ');
            out.print(s);
        }

        var commandObject = getCommandObject();
        log.info("onSuccess() w/ subCommandChain: {} & commandObject:{}", subCommandChain, commandObject);
        var adapter = propertyAccess.getAdapter(commandObject);
        for (String propertyName : adapter.getPropertyNames())
        {
            var value = adapter.get(commandObject, propertyName);
            if (value == null || propertyName.equals("class")) {
                continue;
            }
            // Intent is to transform "some_thing" into "--some-thing"
            var transformed = "--" + propertyName.replace("_", "-");
            log.debug("{}: {} {}", propertyName, transformed, value);

            String string = value.toString();
            //var type = adapter.getPropertyAdapter(propertyName).getType();

            // TODO: we need something more elaborate to transform "stuff" into string values for CLI execution
            if (value instanceof Enum)
            {
                if (string != null && string.startsWith("_"))
                {
                    string = string.substring(1);
                }
            }

            procBuilder.withArg(transformed);
            procBuilder.withArg(string);
            out.print(' ');
            out.print(transformed);
            out.print(' ');
            out.print(string);
        }

        out.println("\n");
        out.flush();
        writer.flush();

        var result = procBuilder.ignoreExitStatus().run();
        var exitValue = result.getExitValue();

        out.println("\nExit status ");
        out.print(exitValue);

        out.flush();
        writer.flush();

        byte[] data = outputStream.toByteArray();

        return new StreamResponse() {
            public String getContentType() {
                return "text/plain; charset=UTF-8";
            }

            public void prepareResponse(Response response) {
                // Optionally set headers
            }

            public InputStream getStream() throws IOException {
                return new ByteArrayInputStream(data);
            }
        };

    }
}
