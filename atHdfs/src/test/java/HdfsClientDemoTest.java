import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClientDemoTest {

    private FileSystem fs;

    @Before
    public void init(){
        Configuration conf=new Configuration();
//        conf.set("fs.defaultFS","hdfs://mini1:9000");
        try {
            fs = FileSystem.get(new URI("hdfs://mini1:9000"),conf,"root" );
//            fs = FileSystem.get(conf);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void upload() throws IOException {
        fs.copyFromLocalFile(new Path("E:\\code\\java\\atshop\\atHdfs\\src\\test\\java\\HdfsClientDemoTest.java"), new Path("/"));
        fs.close();
    }

    @Test
    public void testDownLoad() throws IOException {
        fs.copyToLocalFile(new Path("/pom.xml"),new Path("D:\\aa.xml") );
    }
}
