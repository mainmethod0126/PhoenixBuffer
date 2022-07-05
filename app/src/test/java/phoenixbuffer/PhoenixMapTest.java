package phoenixbuffer;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PhoenixMapTest {

    @Test
    @DisplayName("PhoenixMapTest")
    public void phoenixMap() throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {

        PhoenixMap<String, Object> phoenixMap = new PhoenixMap<>(3, 3000, buffer -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        for (int i = 0; i < 10; i++) {
            phoenixMap.put("testNo : " + i, i);
            System.out.println("testNo : " + i);
        }
    }
}
