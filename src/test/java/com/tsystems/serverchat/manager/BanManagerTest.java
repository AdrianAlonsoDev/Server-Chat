package com.tsystems.serverchat.manager;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 *
 * @author aalonsoa
 */
public class BanManagerTest {

    @Test
    public void testCheckMessageBan()
    {
        BanManager bm = new BanManager(new UserManager());

        String strTest = "Hola, eres una puta";
        bm.checkMessage(strTest);
        System.out.println(strTest);
        assertNotEquals("Hola, eres una puta", strTest);
    }

}
