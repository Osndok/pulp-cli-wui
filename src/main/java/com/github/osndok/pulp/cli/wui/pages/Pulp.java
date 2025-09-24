package com.github.osndok.pulp.cli.wui.pages;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;
import com.github.osndok.pulp.cli.wui.services.ColorPatternService;
import com.github.osndok.pulp.cli.wui.services.HelpDocsService;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PartialTemplateRenderer;
import org.apache.tapestry5.util.TextStreamResponse;
import org.slf4j.Logger;

import java.io.StringWriter;
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
    String[] commandSegments;

    @Property
    private
    HelpDocs helpDocs;

    @Property
    private
    String subCommand;

    @Property
    private
    String contentFrame;

    @Inject
    private
    ColorPatternService colorPatternService;

    @Inject
    private
    HelpDocsService helpDocsService;

    @Inject
    private
    PartialTemplateRenderer partialTemplateRenderer;

    @Inject
    private
    Block defaultIframeBlock;

    public
    Object onActivate(EventContext context)
    {
        commandSegments = context.toStrings();

        if (commandSegments.length == 0)
        {
            topName = "pulp";
        }
        else
        {
            topName = commandSegments[commandSegments.length - 1];
        }

        helpDocs = helpDocsService.getHelpDocsFor(commandSegments);
        contentFrame = "frame" + UUID.randomUUID().toString().hashCode();

        return null;
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
    String getLeftNavWidth()
    {
        // TODO: consider deriving navigation width based on longest subcommand
        return topName.length() + "em";
    }

    public
    String getDefaultIframeContents()
    {
        return partialTemplateRenderer.render(defaultIframeBlock);
    }
}
