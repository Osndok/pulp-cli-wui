package com.github.osndok.pulp.cli.wui.pages.pulp.rpm.remote;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;

public
class RpmRemoteCreate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String name;
        public String parameter_one;
        public String parameter_two;
    }

    private Object command = new Command();

    @Override
    public
    Object getCommandObject()
    {
        return command;
    }

    @Override
    public
    Class getNextPage()
    {
        // TODO: Land them in the rpm-list view
        return RpmRemoteCreate.class;
    }
}
