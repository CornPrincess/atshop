import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HdfsClientDemoTest {

    private FileSystem fs;

    @Before
    public void init(){
        Configuration conf=new Configuration();
        conf.set("fs.defaultFS","hdfs://mini1:9000");
        try {
            fs = FileSystem.get(conf);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void upload() throws IOException {
        fs.copyFromLocalFile(new Path("E:\\code\\java\\atshop\\atservice\\pom.xml"), new Path("/"));
        fs.close();

    }
}
