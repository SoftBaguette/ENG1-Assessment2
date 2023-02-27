
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import junit.*;

public class BurgerUnitTest {
    
    @Test
    public void testChefAssetExists(){
        assertTrue("This test will only pass if chef sprite exists", Gdx.file.internal("Chef_holding.png"));
    }

}
