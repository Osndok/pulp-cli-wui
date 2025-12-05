package com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;

public
class MavenDistributionUpdate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String href;
        public String name;
        public String distribution;
        public String remote;
        public String repository;
        public String labels;
        public String base_path;
    }

    private Command command;

    @Override
    public
    Object getCommandObject()
    {
        if (command == null)
        {
            command = new Command();
        }
        return command;
    }

    public
    void onActivate(String pulp_href)
    {
        command = new Command();
        command.href = pulp_href;
    }
}

