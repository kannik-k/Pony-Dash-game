package ee.taltech.pony_dash_for_spikes_salvation;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import ee.taltech.pony_dash_for_spikes_salvation.packets.Packet;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketPlayerConnect;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketSendCoordinates;

public class Network {
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Packet.class);
        kryo.register(PacketPlayerConnect.class);
        kryo.register(PacketSendCoordinates.class);
    }
}

