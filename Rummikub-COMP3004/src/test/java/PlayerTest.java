import junit.framework.TestCase;
 
public class PlayerTest extends TestCase{
	
public void  testTurn() { 
		
		Player player = new Player ();
		player.setIsItPlayersTurn(true); 
		assertTrue(player.getIsItPlayersTurn()); 
		player.setIsItPlayersTurn(false); 
		assertFalse(player.getIsItPlayersTurn()); 
	}
	
	public void testHasTileBeenTaken() { 
		
		Player player = new Player ();
		assertFalse(player.getHasTileBeenTaken()); 
		player.setHasTileBeenTaken(true); 
		assertTrue(player.getHasTileBeenTaken()); 
	}
	
	public void testHasTileBeenPlaced() { 
		
		Player player = new Player ();
		assertFalse(player.getHasTileBeenPlaced()); 
		player.setHasTileBeenPlaced(true); 
		assertTrue(player.getHasTileBeenPlaced()); 
	}

}
