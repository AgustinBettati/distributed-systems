import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Response;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Watcher {

    public static void main(String[] args) throws IOException {

        Client etcdClient= Client.builder().endpoints("http://localhost:2379").build();
        ByteSequence SERVICE_PATH = ByteSequence.from("services/product/".getBytes());

        etcdClient.getWatchClient().watch(SERVICE_PATH, WatchOption.newBuilder().withPrefix(SERVICE_PATH).build(), new Watch.Listener() {
            @Override
            public void onNext(WatchResponse response) {
                for (WatchEvent event: response.getEvents()){
                    System.out.println("key:"+event.getKeyValue().getKey().toString(Charset.forName("UTF-8"))+",value:"+event.getKeyValue().getValue().toString(Charset.forName("UTF-8")));
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

        System.in.read();
    }
}
