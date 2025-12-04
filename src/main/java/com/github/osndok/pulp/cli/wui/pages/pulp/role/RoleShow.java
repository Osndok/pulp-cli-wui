package com.github.osndok.pulp.cli.wui.pages.pulp.role;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;

public
class RoleShow extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String href;
        public String name;
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
