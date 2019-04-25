import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EtcdServiceWatcher {

    private Client etcdClient;
    private ByteSequence SERVICE_PATH;
    private String serviceName;


    public EtcdServiceWatcher(String serviceName) {
        etcdClient= Client.builder().endpoints("http://localhost:2379").build();
        SERVICE_PATH = ByteSequence.from(("services/" +serviceName + "/").getBytes());
        this.serviceName = serviceName;
    }

    public List<Integer> obtainPorts() throws ExecutionException, InterruptedException {
        List<Integer> portsList = new ArrayList<>();

        KV kvClient = etcdClient.getKVClient();
        ByteSequence key = ByteSequence.from(("services/" + serviceName).getBytes());
        GetResponse getResponse = kvClient.get(key, GetOption.newBuilder().withPrefix(key).build()).get();

        getResponse.getKvs().forEach(keyValue -> {
            String port = keyValue.getValue().toString(Charset.forName("UTF-8"));
            portsList.add(Integer.parseInt(port));
        });

        etcdClient.getWatchClient().watch(SERVICE_PATH, WatchOption.newBuilder().withPrefix(SERVICE_PATH).build(), new Watch.Listener() {
            @Override
            public void onNext(WatchResponse response) {
                for (WatchEvent event: response.getEvents()){
                    String newPort = event.getKeyValue().getValue().toString(Charset.forName("UTF-8"));
                    System.out.println("key:"+event.getKeyValue().getKey().toString(Charset.forName("UTF-8"))+",value:"+ newPort);
                    portsList.add(Integer.parseInt(newPort));
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

        return portsList;
    }
}

