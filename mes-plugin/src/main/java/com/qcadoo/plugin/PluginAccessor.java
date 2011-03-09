package com.qcadoo.plugin;

import java.util.Collection;

public interface PluginAccessor {

    Plugin getEnabledPlugin(String pluginKey);

    Collection<Plugin> getEnabledPlugins();

    Plugin getPlugin(String name);

    Collection<Plugin> getPlugins();

}
