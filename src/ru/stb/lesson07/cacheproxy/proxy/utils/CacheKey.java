package ru.stb.lesson07.cacheproxy.proxy.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CacheKey implements Externalizable {
    private static final long serialVersionUID = -8259002486088424853L;

    private String methodName;
    private Object[] args;

    public CacheKey(){
    }

    public CacheKey(String methodName, Object[] args) {
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public int hashCode() {
        if(methodName == null || args == null)
            throw new IllegalStateException("Не заданы поля класса CacheKey");
        int hash = methodName.hashCode();
        for (Object arg : args) {
            hash ^= arg.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if(methodName == null || args == null)
            throw new IllegalStateException("Не заданы поля класса CacheKey");

        CacheKey that = (CacheKey) obj;
        if(!this.methodName.equals(that.methodName)) return false;
        if(this.args.length != that.args.length) return false;
        for (int i = 0; i < args.length; i++) {
            if(!this.args[i].equals(that.args[i])) return false;
        }

        return true;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(methodName);
        out.writeInt(args.length);
        for (Object arg : args) {
            out.writeObject(arg);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        methodName = (String) in.readObject();
        int argCount = in.readInt();
        args = new Object[argCount];
        for (int i = 0; i < argCount; i++) {
            args[i] = in.readObject();
        }
    }
}
