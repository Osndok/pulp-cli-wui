package com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;

public
class MavenDistributionShow extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String href;
        public String name;
        public String distribution;
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
