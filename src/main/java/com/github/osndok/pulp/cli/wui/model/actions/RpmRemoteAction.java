package com.github.osndok.pulp.cli.wui.model.actions;

import com.github.osndok.pulp.cli.wui.pages.pulp.rpm.remote.RpmRemoteDestroy;

/**
 * Things that one might want to do to an EXISTING rpm-remote.
 * Basically everything but 'create' since you can't create something that already exists.
 */
public
enum RpmRemoteAction
        implements ActionEnum
{
    destroy(RpmRemoteDestroy.class),
    label_set,
    label_unset,
    role_add,
    role_list,
    role_my_permissions,
    role_remove,
    show,
    update;

    private final Class actionPageClass;

    RpmRemoteAction()
    {
        actionPageClass = null;
    }

    RpmRemoteAction(Class actionPageClass)
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
