package ru.stb.lesson06.pluginmanager;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        String pluginRootDirectory = "C:\\Projects\\Java\\L6\\Plugins";
        PluginManager pluginManager = new PluginManager(pluginRootDirectory);

        /**
         * Поиск и загрузка плагинов
         */
        File dir = new File(pluginRootDirectory);

        File[] plugins = dir.listFiles();
        if(plugins != null) {
            for (File pluginName : plugins) {
                String pluginClassName = pluginName.getName() + ".class";
                try {
                    Plugin plugin = pluginManager.load(pluginName.getName(), pluginClassName);
                    plugin.doUsefull();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
