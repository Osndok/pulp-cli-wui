package com.github.osndok.pulp.cli.wui.model.actions;

import com.github.osndok.pulp.cli.wui.pages.pulp.role.RoleDestroy;
import com.github.osndok.pulp.cli.wui.pages.pulp.role.RoleShow;
import com.github.osndok.pulp.cli.wui.pages.pulp.role.RoleUpdate;

public
enum RoleAction implements ActionEnum
{
    destroy(RoleDestroy.class),
    show(RoleShow.class),
    update(RoleUpdate.class),
    ;

    private final Class actionPageClass;

    RoleAction(final Class actionPageClass)
    {
        this.actionPageClass = actionPageClass;
    }

    @Override
    public
    Class getActionPageClass()
    {
        return actionPageClass;
    }
}
