package com.github.osndok.pulp.cli.wui.services.impl;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;
import com.github.osndok.pulp.cli.wui.services.HelpDocsService;

import java.util.List;

public
class HelpDocsServiceImpl implements HelpDocsService
{
    @Override
    public
    HelpDocs getHelpDocsFor(final String[] subCommandChain)
    {
        var retval = new HelpDocs();
        retval.subCommands = List.of("one", "two", "three", "four", "five", "six", "seven", "eight");
        return retval;
    }
}
