import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Streams;

import java.io.IOException;
import java.io.InputStream;

public class ListAllPods {
    public static void main(String[] args) throws IOException, ApiException, InterruptedException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreApi = new CoreV1Api(client);

        PodLogs logs = new PodLogs();
        V1PodList podList =
                coreApi
                        .listNamespacedPod(
                                "default", "false", null, null, null, null, null, null, null, null, null);
        for(V1Pod p : podList.getItems()){
            System.out.println(p.getMetadata().getLabels());
        }
        InputStream is = logs.streamNamespacedPodLog(podList.getItems().get(0));
        Streams.copy(is, System.out);
    }
}