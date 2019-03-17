import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class ATest {

    @Test
    public void test1() {
        assert(1==1);
        Properties p = new Properties();
        try {
            p.load(new FileReader("bd.config"));

        }catch (IOException e){
            assert false;
        }
    }

    @Test
    public void test2() {
        assert(2==2);
    }
}