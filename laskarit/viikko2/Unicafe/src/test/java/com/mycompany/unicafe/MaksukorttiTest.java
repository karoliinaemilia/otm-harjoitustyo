package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikeinAlussa() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("saldo: 0.35", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikein() {
        
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.05", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosOttaaLiikaa() {
        kortti.otaRahaa(20);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahaRiittaa() {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test 
    public void otaRahaaPalauttaaFalseJosRahaEiRiita() {
        assertFalse(kortti.otaRahaa(20));
    }
    
    @Test
    public void saldoPalauttaaSaldon() {
        assertEquals(10, kortti.saldo());
    }
}
