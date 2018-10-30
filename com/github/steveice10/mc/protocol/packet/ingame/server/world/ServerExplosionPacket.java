/*
 * Decompiled with CFR 0_132.
 */
package com.github.steveice10.mc.protocol.packet.ingame.server.world;

import com.github.steveice10.mc.protocol.data.game.world.block.ExplodedBlockRecord;
import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerExplosionPacket
extends MinecraftPacket {
    public float x;
    public float y;
    public float z;
    public float radius;
    public List<ExplodedBlockRecord> exploded;
    public float pushX;
    public float pushY;
    public float pushZ;

    public ServerExplosionPacket() {
    }

    public ServerExplosionPacket(float x, float y, float z, float radius, List<ExplodedBlockRecord> exploded, float pushX, float pushY, float pushZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.exploded = exploded;
        this.pushX = pushX;
        this.pushY = pushY;
        this.pushZ = pushZ;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getRadius() {
        return this.radius;
    }

    public List<ExplodedBlockRecord> getExploded() {
        return this.exploded;
    }

    public float getPushX() {
        return this.pushX;
    }

    public float getPushY() {
        return this.pushY;
    }

    public float getPushZ() {
        return this.pushZ;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.z = in.readFloat();
        this.radius = in.readFloat();
        this.exploded = new ArrayList<ExplodedBlockRecord>();
        int length = in.readInt();
        for (int count = 0; count < length; ++count) {
            this.exploded.add(new ExplodedBlockRecord(in.readByte(), in.readByte(), in.readByte()));
        }
        this.pushX = in.readFloat();
        this.pushY = in.readFloat();
        this.pushZ = in.readFloat();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeFloat(this.x);
        out.writeFloat(this.y);
        out.writeFloat(this.z);
        out.writeFloat(this.radius);
        out.writeInt(this.exploded.size());
        for (ExplodedBlockRecord record : this.exploded) {
            out.writeByte(record.getX());
            out.writeByte(record.getY());
            out.writeByte(record.getZ());
        }
        out.writeFloat(this.pushX);
        out.writeFloat(this.pushY);
        out.writeFloat(this.pushZ);
    }
}
