package ru.stb.lesson06.pluginmanager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader extends ClassLoader {
    private final String jarFileName;
    private final Map<String, Class<?>> cache = new HashMap<>();
    private final HashSet<String> definedClasses = new HashSet<>();

    public PluginLoader(ClassLoader parent, String jarFileName) {
        super(parent);
        this.jarFileName = jarFileName;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte bytes[] = loadClassFromFile(jarFileName + "\\" + className + "\\" + className + ".class");
            return defineClass(className, bytes, 0, bytes.length);
        } catch (IOException e) {
            return super.findClass(className);
        }
    }

    /**
     * Загрузка класса из файла в виде последовательности байт
     */
    private byte[] loadClassFromFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        long length = Files.size(path);
        if (length > Integer.MAX_VALUE)
            throw new IOException("Слишком большой размер файла " + fileName);
        byte[] bytes = new byte[(int)length];

        InputStream is = Files.newInputStream(path);
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length)
            throw new IOException("Не удалось прочитать файл " + fileName);

        return bytes;
    }

    /**
     * Загрузка класса из jar-файле
     */
    private byte[] loadClassFromJar(String fileName) throws IOException {
        JarFile jarFile = new JarFile(fileName);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (jarEntry.isDirectory())
                continue;

            long length = jarEntry.getSize();
            if (length > Integer.MAX_VALUE)
                throw new IOException("Слишком большой размер файла " + fileName);

            if (jarEntry.getName().endsWith(".class")) {
                String clName = jarEntry.getName().replace('/', '.').substring(0, jarEntry.getName().length() - 6);
                byte[] bytes = jarEntry.getExtra();
                return bytes;
            }
        }
        return null;
    }
}
