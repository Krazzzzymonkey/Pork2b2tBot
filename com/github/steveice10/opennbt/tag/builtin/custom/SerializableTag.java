/*
 * Decompiled with CFR 0_132.
 */
package com.github.steveice10.opennbt.tag.builtin.custom;

import com.github.steveice10.opennbt.tag.builtin.Tag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SerializableTag
extends Tag {
    private Serializable value;

    public SerializableTag(String name) {
        this(name, Integer.valueOf(0));
    }

    public SerializableTag(String name, Serializable value) {
        super(name);
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    @Override
    public void read(DataInput in) throws IOException {
        ObjectInputStream str = new ObjectInputStream(new DataInputInputStream(in));
        try {
            this.value = (Serializable)str.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Class not found while reading SerializableTag!", e);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        ObjectOutputStream str = new ObjectOutputStream(new DataOutputOutputStream(out));
        str.writeObject(this.value);
    }

    @Override
    public SerializableTag clone() {
        return new SerializableTag(this.getName(), this.getValue());
    }

    private static class DataOutputOutputStream
    extends OutputStream {
        private DataOutput out;

        public DataOutputOutputStream(DataOutput out) {
            this.out = out;
        }

        @Override
        public void write(int b) throws IOException {
            this.out.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            this.out.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.out.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }

    private static class DataInputInputStream
    extends InputStream {
        private DataInput in;

        public DataInputInputStream(DataInput in) {
            this.in = in;
        }

        @Override
        public int read() throws IOException {
            return this.in.readUnsignedByte();
        }

        @Override
        public int read(byte[] b) throws IOException {
            this.in.readFully(b);
            return b.length;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            this.in.readFully(b, off, len);
            return len;
        }

        @Override
        public long skip(long l) throws IOException {
            return this.in.skipBytes((int)l);
        }

        @Override
        public int available() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public synchronized void mark(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public synchronized void reset() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean markSupported() {
            return false;
        }
    }

}
