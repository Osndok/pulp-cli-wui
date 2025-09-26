package com.github.osndok.pulp.cli.wui.model;

/**
 Theory: Using a standard "Boolean", we will get a default value of 'false' (b/c the box is
 left unchecked), but if we use an ENUM, then we will get a default value of "null" (b/c the
 user did not select either true or false).

 Wherever possible, we don't want to silently add or remove stuff the user did not specify,
 but instead push that off onto the pulp CLI itself, particularly since we don't know what
 any of the defaults ought to be, and it might make a significant difference to some workflows!
 */
public
enum BooleanEnum
{
    _true,
    _false
}
