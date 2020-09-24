import seamcurving.demo.controller.UploadUrl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class downloadPic {
    private String url;
    private String path;

    public downloadPic() {
        UploadUrl u = new UploadUrl();
        url = u.getAns();
    }

    public void download() throws IOException {
        URL u = new URL(url);
        InputStream in = new BufferedInputStream(u.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        path = "D:/SeamCurvingWebVersion/demo/frontend/data/image/1.jpg";
        FileOutputStream file = new FileOutputStream(path);
        file.write(response);
        file.close();
    }






}
