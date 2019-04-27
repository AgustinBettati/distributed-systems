import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class EtcdServiceWatcher<T> {

    private Client etcdClient;
    private ByteSequence SERVICE_PATH;
    private String serviceName;
    private FromPortToStub<T> stubSupplier;
    private HashMap<Integer, T> portWithStub;


    public EtcdServiceWatcher(String serviceName, FromPortToStub<T> stubSupplier) {
        etcdClient= Client.builder().endpoints("http://localhost:2379").build();
        SERVICE_PATH = ByteSequence.from(("services/" +serviceName + "/").getBytes());
        this.serviceName = serviceName;
        this.stubSupplier = stubSupplier;
        this.portWithStub = new HashMap<>();
    }

    public Map<Integer, T> obtainStubs() throws ExecutionException, InterruptedException {

        KV kvClient = etcdClient.getKVClient();
        ByteSequence key = ByteSequence.from(("services/" + serviceName).getBytes());
        GetResponse getResponse = kvClient.get(key, GetOption.newBuilder().withPrefix(key).build()).get();

        getResponse.getKvs().forEach(keyValue -> {
            String portString = keyValue.getValue().toString(Charset.forName("UTF-8"));
            final int port = Integer.parseInt(portString);
            portWithStub.put(port, stubSupplier.fromPortToStub(port));
        });

        etcdClient.getWatchClient().watch(SERVICE_PATH, WatchOption.newBuilder().withPrefix(SERVICE_PATH).build(), new Watch.Listener() {
            @Override
            public void onNext(WatchResponse response) {
                for (WatchEvent event: response.getEvents()){
                    String newPort = event.getKeyValue().getValue().toString(Charset.forName("UTF-8"));
                    if(event.getEventType().toString().equals("DELETE")){
                        final String[] array = event.getKeyValue().getKey().toString(Charset.forName("UTF-8")).split("/");
                        final String removedPort = array[2];
                        portWithStub.remove(Integer.parseInt(removedPort));
                        System.out.println("an instance of "+serviceName+" with port: "+ removedPort + " has died and was eliminated");
                    }
                    else if (!newPort.isEmpty()){
                        System.out.println("registered new port for service "+serviceName+" with port:"+ newPort);
                        final int port = Integer.parseInt(newPort);
                        portWithStub.put(port, stubSupplier.fromPortToStub(port));
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

        });
        return portWithStub;
    }

    public T obtainStub(){
        final Object[] keys = this.portWithStub.keySet().toArray();
        int index = new Random().nextInt(keys.length);
        return portWithStub.get(keys[index]);
    }
}

