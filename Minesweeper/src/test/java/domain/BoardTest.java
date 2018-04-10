
package domain;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class BoardTest {
    
    @Test
    public void testBoardClassExists() {
        try {
            Class.forName("minesweeper.domain.Board");
        } catch (ClassNotFoundException e) {
            Assert.fail("Class doesn't exist");
        }
    }
}
