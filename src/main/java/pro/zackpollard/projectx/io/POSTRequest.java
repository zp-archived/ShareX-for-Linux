package pro.zackpollard.projectx.io;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import pro.zackpollard.projectx.uploaders.Uploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author DarkSeraphim
 */
public class POSTRequest extends NetworkRequest
{

    private HttpPost post;

    POSTRequest(Uploader uploader, File file) {
        super(uploader);
        this.init(file);
    }

    private final void init(File file) {
        this.post = new HttpPost(this.uploader.getUrl());
        for(Map.Entry<String, String> header : this.uploader.getOptionalHeaders().entrySet()) {
            this.post.addHeader(header.getKey(), header.getValue());
        }

        for(Map.Entry<String, String> kvpair : this.uploader.getOptionalParams().entrySet()) {
            this.urlParameters.addTextBody(kvpair.getKey(), kvpair.getValue());
        }
        this.urlParameters.addBinaryBody(this.uploader.getFile(), file);
        this.post.setEntity(this.urlParameters.build());
    }

    @Override
    public String run() {
        HttpClientBuilder buildClient = HttpClientBuilder.create();
        HttpClient client = buildClient.build();
        HttpResponse response;
        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {
            response = client.execute(post);
            in = response.getEntity().getContent();
            byte[] buf = new byte[16];
            int len;
            while((len = in.read(buf)) > 0) {
                sb.append(new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(in != null)
                    in.close();
            } catch(IOException ex) {
            }
        }
        return sb.toString();
    }
}
