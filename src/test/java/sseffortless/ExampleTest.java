package sseffortless;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExampleTest {

    @Autowired
    private Example example;

    @Test
    public void success() {
        Assert.assertTrue(example.isReady());
    }
}
