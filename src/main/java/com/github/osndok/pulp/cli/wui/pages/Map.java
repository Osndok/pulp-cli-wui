package com.github.osndok.pulp.cli.wui.pages;

import com.github.osndok.pulp.cli.wui.services.HelpDocsService;
import io.vavr.collection.Vector;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;
import org.slf4j.Logger;

/**
 * Takes a LONG time to build a simple list of all the available CLI commands.
 */
public
class Map
{
    @Inject
    private
    Logger log;

    @Inject
    private
    HelpDocsService helpDocsService;

    @ActivationRequestParameter("tree")
    private boolean tree;

    public
    Object onActivate()
    {
        var accumulator = new StringBuilder();

        buildFlatMap(Vector.empty(), accumulator);

        return new TextStreamResponse("text/plain", accumulator.toString());
    }

    private
    void buildFlatMap(final Vector<String> superCommand, final StringBuilder accumulator)
    {
        log.debug("buildFlatMap: {}", superCommand);
        var info = helpDocsService.getHelpDocsFor(superCommand.toJavaArray(String[]::new));
        if (info.subCommands.isEmpty()) {
            println(accumulator, superCommand);
        } else {
            for (String subCommand : info.subCommands)
            {
                buildFlatMap(superCommand.append(subCommand), accumulator);
            }
        }
    }

    private
    void println(final StringBuilder accumulator, final Vector<String> command)
    {
        boolean first = true;
        for (String element : command)
        {
            if (first) {
                accumulator.append(element);
                first = false;
            } else {
                accumulator.append(' ');
                accumulator.append(element);
            }
        }
        accumulator.append('\n');
    }
}
