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

package net.daporkchop.toobeetooteebot.util.cache.data.entity;

import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEffectPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import com.github.steveice10.packetlib.packet.Packet;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author DaPorkchop_
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class EntityEquipment extends Entity {
    protected ArrayList<PotionEffect> potionEffects = new ArrayList<>();
    protected Map<EquipmentSlot, ItemStack> equipment = new EnumMap<>(EquipmentSlot.class);
    protected float health;

    {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.equipment.put(slot, null);
        }
    }

    @Override
    public void addPackets(@NonNull Consumer<Packet> consumer) {
        this.potionEffects.forEach(effect -> consumer.accept(new ServerEntityEffectPacket(
                this.entityId,
                effect.getEffect(),
                effect.getAmplifier(),
                effect.getDuration(),
                effect.isAmbient(),
                effect.isShowParticles()
        )));
        this.equipment.forEach((slot, stack) -> consumer.accept(new ServerEntityEquipmentPacket(this.entityId, slot, stack)));
        super.addPackets(consumer);
    }
}
