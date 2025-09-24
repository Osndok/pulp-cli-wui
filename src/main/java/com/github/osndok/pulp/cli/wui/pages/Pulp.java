package com.github.osndok.pulp.cli.wui.pages;

import com.github.osndok.pulp.cli.wui.services.ColorPatternService;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public
class Pulp
{
    @Inject
    private
    Logger log;

    @Property
    private
    String topName;

    @Property
    private
    List<String> commandSegments;

    @Property
    private
    List<String> subCommands;

    @Property
    private
    String subCommand;

    @Property
    private
    String contentFrame;

    @Inject
    private
    ColorPatternService colorPatternService;

    public
    Object onActivate(EventContext context)
    {
        commandSegments = extractCommandSegments(context);
        topName = commandSegments.get(commandSegments.size()-1);
        subCommands = List.of("one", "two", "three", "four", "five", "six", "seven");
        contentFrame = "frame" + UUID.randomUUID().toString().hashCode();

        if (topName.equals("--help"))
        {
            var message = new StringBuilder("Exec? ");
            message.append(String.join(" ", commandSegments));
            return new TextStreamResponse("text/plain", message.toString());
        }

        return null;
    }

    private static
    List<String> extractCommandSegments(final EventContext context)
    {
        var retval = new ArrayList<String>();
        retval.add("pulp");
        int count = context.getCount();

        for (int i = 0; i < count; i++)
        {
            // read typed value; for plain path segments use String.class
            String segment = context.get(String.class, i);
            retval.add(segment);
        }

        return retval;
    }

    public
    String getPrimaryBgColor()
    {
        //return "#f7f7f7";
        return colorPatternService.getCssColorFor(topName);
    }

    public
    String getSubCommandBgColor()
    {
        // TODO: derive color from this.subCommand
        //return "aliceblue";
        return colorPatternService.getCssColorFor(subCommand);
    }

    public
    String getSubCommandLink()
    {
        // TODO: derive link from this.subCommand
        return "#";
    }

    public
    String getInitialIframeHelpLink()
    {
        // TODO: derive color from initial link pluss "--help" parameter
        return "about:mozilla";
    }

    public
    String getLeftNavWidth()
    {
        // TODO: consider deriving navigation width based on longest subcommand
        return topName.length() + "em";
    }
}
