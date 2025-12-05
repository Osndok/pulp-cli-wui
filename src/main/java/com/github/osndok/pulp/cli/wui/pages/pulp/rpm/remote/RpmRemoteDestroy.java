package com.github.osndok.pulp.cli.wui.pages.pulp.rpm.remote;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;

public
class RpmRemoteDestroy extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String href;
        public String name;
        public String remote;
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
