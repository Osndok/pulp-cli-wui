package com.github.osndok.pulp.cli.wui.services.impl;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;
import com.github.osndok.pulp.cli.wui.services.HelpDocsService;
import org.buildobjects.process.ProcBuilder;

import java.util.ArrayList;
import java.util.List;

public
class HelpDocsServiceImpl implements HelpDocsService
{
    @Override
    public
    HelpDocs getHelpDocsFor(final String[] subCommandChain)
    {
        var result = new ProcBuilder("pulp")
                .withArgs(subCommandChain)
                .withArg("--help")
                .run();

        String[] lines = result.getOutputString().split("\\R"); // Split on any line break
        List<String> beforeCommands = new ArrayList<>();
        List<String> afterCommands = new ArrayList<>();
        boolean foundCommands = false;

        for (String line : lines) {
            if (!foundCommands) {
                if (line.trim().equals("Commands:")) {
                    foundCommands = true;
                } else {
                    beforeCommands.add(line);
                }
            } else {
                afterCommands.add(line.trim());
            }
        }

        var retval = new HelpDocs();
        retval.mainBody = String.join("\n", beforeCommands);
        retval.subCommands = afterCommands;
        return retval;
    }
}
