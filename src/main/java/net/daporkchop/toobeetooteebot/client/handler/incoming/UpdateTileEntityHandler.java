/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2019 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.toobeetooteebot.client.handler.incoming;

import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTileEntityPacket;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import lombok.NonNull;
import net.daporkchop.lib.unsafe.PUnsafe;
import net.daporkchop.toobeetooteebot.client.PorkClientSession;
import net.daporkchop.toobeetooteebot.util.handler.HandlerRegistry;

/**
 * @author DaPorkchop_
 */
public class UpdateTileEntityHandler implements HandlerRegistry.IncomingHandler<ServerUpdateTileEntityPacket, PorkClientSession> {
    protected static final long COLUMN_TILEENTITIES_OFFSET = PUnsafe.pork_getOffset(Column.class, "tileEntities");

    @Override
    public boolean apply(@NonNull ServerUpdateTileEntityPacket packet, @NonNull PorkClientSession session) {
        Column column = CACHE.getChunkCache().get(packet.getPosition().getX() >> 4, packet.getPosition().getZ() >> 4);
        CompoundTag[] oldArray = column.getTileEntities();
        int index = -1;
        for (int i = oldArray.length - 1; i >= 0; i--)  {
            if (oldArray[i].<IntTag>get("x").getValue() == packet.getPosition().getX()
                    && oldArray[i].<IntTag>get("y").getValue() == packet.getPosition().getY()
                    && oldArray[i].<IntTag>get("z").getValue() == packet.getPosition().getZ())  {
                index = i;
                break;
            }
        }
        CompoundTag[] newArray;
        if (packet.getNBT() == null)    {
            if (index == -1)    {
                newArray = oldArray;
            } else {
                newArray = new CompoundTag[oldArray.length - 1];
                System.arraycopy(oldArray, 0, newArray, 0, index - 1);
                System.arraycopy(oldArray, index + 1, newArray, index, oldArray.length - index - 1);
            }
        } else {
            if (index == -1)    {
                newArray = new CompoundTag[oldArray.length + 1];
                System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
                newArray[oldArray.length] = packet.getNBT();
            } else {
                newArray = oldArray;
                newArray[index] = packet.getNBT();
            }
            packet.getNBT().put(new IntTag("x", packet.getPosition().getX()));
            packet.getNBT().put(new IntTag("y", packet.getPosition().getY()));
            packet.getNBT().put(new IntTag("z", packet.getPosition().getZ()));
        }
        PUnsafe.putObject(column, COLUMN_TILEENTITIES_OFFSET, newArray);
        return true;
    }

    @Override
    public Class<ServerUpdateTileEntityPacket> getPacketClass() {
        return ServerUpdateTileEntityPacket.class;
    }
}
