package pts4.model.socket;

import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Corentin on 31/03/2021 at 01:52
 */

public class DataPacket {

    private Map<String, String> data = new HashMap<>();

    public void write(String key, String value) {
        data.put(key, value);
    }

    public String read(String key) {
        return data.get(key);
    }

    @SneakyThrows
    public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(data);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    @SneakyThrows
    public static DataPacket from(String string) {
        DataPacket packet = new DataPacket();
        byte[] data = Base64.getDecoder().decode(string);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        packet.data = (Map<String, String>) ois.readObject();
        return packet;
    }

}
