package com.github.osndok.pulp.cli.wui.pages.pulp.maven.repository;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;

// TODO: At a minimum, we should pretty-print stdout to appear as pulp-cli does when run interactively.
public
class MavenRepositoryShow extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String href;
        public String name;
        public String repository;
    }

    private Command command = new Command();

    @Override
    public
    Object getCommandObject()
    {
        return command;
    }

    public
    void onActivate(String pulp_href)
    {
        command.href = pulp_href;
    }
}
