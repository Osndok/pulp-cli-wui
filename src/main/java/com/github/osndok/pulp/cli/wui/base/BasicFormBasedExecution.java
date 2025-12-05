package com.github.osndok.pulp.cli.wui.base;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.beanmodel.services.BeanModelSource;
import org.apache.tapestry5.commons.services.PropertyAccess;
import org.apache.tapestry5.http.services.Response;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract
class BasicFormBasedExecution extends BasePage
{
    public abstract Object getCommandObject();

    @Inject
    private
    Logger log;

    @Property
    private
    BeanModel beanModel;

    @Inject
    private
    PropertyAccess propertyAccess;

    @Inject
    private
    ComponentResources componentResources;

    @Inject
    private
    BeanModelSource beanModelSource;

    private
    boolean preview = false;

    public final
    Object onActivate()
    {
        var commandObject = getCommandObject();

        // Any way to get our sub-page's messages, instead of ours?
        var messages = componentResources.getMessages();

        // Ordinarily, the beanEditForm would automatically pull the class from the binding
        // channel, but since we have it as a generic Object here, we have to "manually"
        // get the model from the object's class at runtime.
        beanModel = beanModelSource.createEditModel(commandObject.getClass(), messages);

        return null;
    }

    public final
    Object onSelectedFromPreview()
    {
        log.debug("onSelectedFromPreview()");
        preview = true;
        return null;
    }

    public final
    Object onSuccess() throws Exception
    {
        var subCommandChain = getSubCommandChain();
        log.debug("onSuccess(): {}", subCommandChain);

        var outputStream = new ByteArrayOutputStream();
        var commandStdout = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        var out = new PrintWriter(writer);

        out.print("\n$ pulp");
        var procBuilder = new ProcBuilder("pulp")
                .withOutputStream(commandStdout)
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

        if (preview)
        {
            return toStreamResponse(outputStream);
        }

        // EXECUTE
        var result = procBuilder.ignoreExitStatus().run();
        var exitValue = result.getExitValue();

        transferOrTransformStdout(commandStdout, out);

        out.print("\n\nExit status ");
        out.print(exitValue);
        out.print(exitValue == 0 ? " (Success)":" (FAILURE)");

        out.flush();
        writer.flush();

        return toStreamResponse(outputStream);
    }

    private
    void transferOrTransformStdout(
            final ByteArrayOutputStream stdout,
            final PrintWriter out
    )
    {
        byte[] bytes = stdout.toByteArray();

        if (bytes.length == 0)
        {
            log.debug("no stdout");
            return;
        }

        if (bytes[0] == '{')
        {
            log.debug("beautifying stdout, found to be a json object");
            var json = new String(bytes, Charset.defaultCharset());
            try
            {
                new JSONObject(json).prettyPrint(out);
            }
            catch (Exception e)
            {
                log.debug("not json?", e);
                out.print(json);
            }
            return;
        }

        var probablyTextual = new String(bytes, Charset.defaultCharset());
        out.print(probablyTextual);
    }

    private static
    StreamResponse toStreamResponse(final ByteArrayOutputStream outputStream)
    {
        byte[] data = outputStream.toByteArray();

        return new StreamResponse()
        {
            public
            String getContentType()
            {
                return "text/plain; charset=UTF-8";
            }

            public
            void prepareResponse(Response response)
            {
                // Optionally set headers
            }

            public
            InputStream getStream() throws IOException
            {
                return new ByteArrayInputStream(data);
            }
        };
    }
}
