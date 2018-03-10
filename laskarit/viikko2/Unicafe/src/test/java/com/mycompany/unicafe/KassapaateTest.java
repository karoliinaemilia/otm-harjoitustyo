
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    
    @Test
    public void rahamaaraOikeinAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void edullisiaLounaitaMyytyAlussaNolla() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maaukkaitaLounaitaMyytyAlussaNolla() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisOstoToimiiEdulliselleLounaalleJosRahaaTarpeeksi() {
        // vaihtoraha oikein
        assertEquals(60,paate.syoEdullisesti(300));
        // kassan raha oikein
        assertEquals(100240, paate.kassassaRahaa());      
    }
    
    @Test
    public void kateisOstoToimiiEdulliselleLounaalleMyydytLounaatKasvaaJosMaksuTarpeeksi() {
        paate.syoEdullisesti(240);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisOstoToimiiEdulliselleLounaalleJosMaksuEiRiita() {
        // rahat palautetaan
        assertEquals(230, paate.syoEdullisesti(230));
        // kassan raha ei muutu
        assertEquals(100000, paate.kassassaRahaa());     
    }
    
    @Test
    public void kateisOstoToimiiEdulliselleLounaalleMyydytLounaatEivatKasvaJosMaksuEiRiittava() {
        paate.syoEdullisesti(230);
        assertEquals(0, paate.edullisiaLounaitaMyyty());   
    }
    
    @Test  
    public void kateisOstoToimiiMaukkaalleLounaalleJosRahaaTarpeeksi() {
        // vaihtoraha oikein
        assertEquals(50, paate.syoMaukkaasti(450));
        // kassan raha oikein
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisOstoToimiiMaukkaalleLounaalleMyydytLounaatKasvaaJosMaksuTarpeeksi() {
        paate.syoMaukkaasti(400);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisOstoToimiiMaukkaalleLounaalleJosMaksuEiTarpeeksi() {
        // rahat palautetaan
        assertEquals(300, paate.syoMaukkaasti(300));
        // kassan raha ei muutu
        assertEquals(100000, paate.kassassaRahaa());       
    }
    
    @Test
    public void kateisOstoToimiiMaukkaalleLounaalleMyydytLounaatEivatKasvaJosMaksuEiRiita() {
        paate.syoMaukkaasti(300);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoToimiiEdulliselleLounaalleJosRahaaTarpeeksi() {
        assertTrue(paate.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
    }
    
    @Test 
    public void korttiOstoToimiEdulliselleLounalleMyydytLounaatKasvaaJosMaksuTarpeeksi() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoToimiiEdulliselleLounaalleJosMaksuEiTarpeeksi() {
        kortti.otaRahaa(350);
        assertFalse(paate.syoEdullisesti(kortti));
        assertEquals(50, kortti.saldo());
    }
    
    @Test
    public void korttiOstoToimiiEdulliselleLounaalleMyydytLounaatEivatKAsvaJosMaksuEiRiittava() { 
        kortti.otaRahaa(350);
        paate.syoEdullisesti(kortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoToimiiEdulliselleLounaalleKassanSaldoEiMuutu() {
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());        
    }
    
    
    @Test
    public void korttiOstoToimiiMaukkaalleLounaalleJosRahaaTarpeeksi() {
        assertTrue(paate.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
    }
    
    @Test 
    public void korttiOstoToimiMaukkaalleLounalleMyydytLounaatKasvaaJosMaksuTarpeeksi() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoToimiiMaukkaalleLounaalleJosMaksuEiTarpeeksi() {
        kortti.otaRahaa(350);
        assertFalse(paate.syoMaukkaasti(kortti));
        assertEquals(50, kortti.saldo());
    }
    
    @Test
    public void korttiOstoToimiiMaukkaalleLounaalleMyydytLounaatEivatKAsvaJosMaksuEiRiittava() { 
        kortti.otaRahaa(350);
        paate.syoMaukkaasti(kortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoToimiiMaukkaalleLounaalleKassanSaldoEiMuutu() {
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());        
    }
    
    @Test
    public void kortilleLatausToimii() {
        paate.lataaRahaaKortille(kortti, 200);
        assertEquals(600, kortti.saldo());
        assertEquals(100200, paate.kassassaRahaa());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaArvoa() {
        paate.lataaRahaaKortille(kortti, -20);
        assertEquals(400, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }      
    
}
