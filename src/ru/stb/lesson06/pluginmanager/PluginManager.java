package ru.stb.lesson06.pluginmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class PluginManager  {
    /**
     * Путь к директории с плагинами
     */
    private final String pluginRootDirectory;
    /**
     * Список загруженных плагинов
     */
    private final List<Plugin> plugins;
    /**
     * Загрузщик плагинов
     */
    private final PluginLoader pluginLoader;

    public Stream<Plugin> getPlugins() {
        return plugins.stream();
    }

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
        this.plugins = new ArrayList<>();
        this.pluginLoader = new PluginLoader(ClassLoader.getSystemClassLoader(), pluginRootDirectory);
    }

    public void loadPlugins() throws IOException {
        Path path = Paths.get(pluginRootDirectory);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            Iterator<Path> iterator = ds.iterator();
            while (iterator.hasNext()) {
                Path p = iterator.next();

            }
        }

        File rootDir = new File(pluginRootDirectory);
        File[] pluginDirs = rootDir.listFiles();
        if (pluginDirs != null) {
            for (File pluginDir : pluginDirs) {
                String pluginClassName = pluginDir.getName() + ".class";
                try {
                    Plugin plugin = load(pluginDir.getName(), pluginClassName);
                    plugins.add(plugin);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Plugin load(String pluginName, String pluginClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = pluginLoader.loadClass(pluginName);
        return (Plugin) clazz.newInstance();
    }
}
