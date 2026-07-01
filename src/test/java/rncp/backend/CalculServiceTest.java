package rncp.backend;

import org.junit.jupiter.api.Test;
import rncp.backend.service.CalculeService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculServiceTest {
    CalculeService calculService = new CalculeService();
    @Test
    public void firstTest() {
        int result = calculService.adition(2,3);
        assertEquals(5,result );
    }
    @Test
    void segonTest(){
        int result = calculService.calculeDeuxNombreNegatif(-4, -3);
        assertEquals(-7,result );

        }
    }

