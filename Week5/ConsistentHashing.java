import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class ConsistentHashing {

    private static final int VIRTUAL_NODES = 5; // 每个真实节点对应的虚拟节点数量
    private final SortedMap<Long, String> hashRing = new TreeMap<>();
    private final List<String> servers;

    public ConsistentHashing(List<String> servers) {
        this.servers = servers;
        for (String server : servers) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                long hash = hash(server + "#" + i);
                hashRing.put(hash, server);
            }
        }
    }

    private long hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
            return ((digest[0] & 0xFFL) << 24) | ((digest[1] & 0xFFL) << 16) | ((digest[2] & 0xFFL) << 8)
                    | (digest[3] & 0xFFL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getServer(String key) {
        long hash = hash(key);
        if (!hashRing.containsKey(hash)) {
            SortedMap<Long, String> tailMap = hashRing.tailMap(hash);
            hash = tailMap.isEmpty() ? hashRing.firstKey() : tailMap.firstKey();
        }
        return hashRing.get(hash);
    }

    public static void main(String[] args) {
        List<String> servers = Arrays.asList("Server1", "Server2", "Server3");
        ConsistentHashing ch = new ConsistentHashing(servers);

        System.out.println("Key1 -> " + ch.getServer("Key1"));
        System.out.println("Key2 -> " + ch.getServer("Key2"));
        System.out.println("Key3 -> " + ch.getServer("Key3"));
    }
}
