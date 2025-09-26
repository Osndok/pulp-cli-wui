package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;
import com.github.osndok.pulp.cli.wui.services.ColorPatternService;
import com.github.osndok.pulp.cli.wui.services.HelpDocsService;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * Provides functionality that just about every page will need, including:
 * 1) parsing page path into a command path,
 * 2) retrieving the help text
 * 3) ... ?
 */
public
class BasePage
{
    @Inject
    private
    Logger log;

    private
    HelpDocs helpDocs;

    private
    String topName;

    private
    String[] subCommandChain;

    @Inject
    private
    HelpDocsService helpDocsService;

    @Inject
    private
    ComponentResources componentResources;

    @Inject
    private
    ColorPatternService colorPatternService;

    @OnEvent("activate")
    public final
    void onActivateBasePage()
    {
        // e.g. pageName = "pulp/rpm/remote/Create"
        String[] segments = componentResources.getPageName().split("/");
        subCommandChain = Arrays.stream(segments, 1, segments.length)
                .map(String::toLowerCase)
                .toArray(String[]::new);
        topName = subCommandChain[subCommandChain.length-1];

        helpDocs = helpDocsService.getHelpDocsFor(subCommandChain);
    }

    public
    HelpDocs getHelpDocs()
    {
        return helpDocs;
    }

    public
    String getTopName()
    {
        return topName;
    }

    public
    String[] getSubCommandChain()
    {
        return subCommandChain;
    }

    public
    String getPrimaryBgColor()
    {
        return colorPatternService.getCssColorFor(getTopName());
    }

}
