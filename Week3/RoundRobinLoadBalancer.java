import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer {
    private List<String> servers; // list of server address
    private AtomicInteger serverIndex; // to track the current server index

    public RoundRobinLoadBalancer(List<String> servers) {
        this.servers = servers;
        this.serverIndex = new AtomicInteger(0);
    }

    public String getNextServer(){
        int index = serverIndex.getAndUpdate(i -> (i+1) % servers.size());
        return servers.get(index);
    }

    public static void main(String[] args) {
        List<String> servers = List.of("Server1", "Server2", "Server3");
        RoundRobinLoadBalancer loadBalancer = new RoundRobinLoadBalancer(servers);

        // Simulate requests
        for(int i = 0; i < 10; i++){
            String server = loadBalancer.getNextServer();
            System.out.println("Request " + (i+1) + " is handled by " + server);
        }
    }
}
