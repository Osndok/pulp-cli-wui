package com.github.osndok.pulp.cli.wui.pages.pulp.role;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import com.github.osndok.pulp.cli.wui.meta.TodoMultiValue;
import org.checkerframework.checker.units.qual.C;

public
class RoleUpdate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String href;
        public String name;
        public String description;
        public Void no_permission;
        @TodoMultiValue
        public String permission;
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
