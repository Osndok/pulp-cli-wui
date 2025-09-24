package com.github.osndok.pulp.cli.wui.services;

import com.github.osndok.pulp.cli.wui.model.HelpDocs;

public
interface HelpDocsService
{
    HelpDocs getHelpDocsFor(String[] subCommandChain);
}
