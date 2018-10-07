import junit.framework.TestCase;

public class TileTest extends TestCase {
	public void testTileExists() {
		// Create a tile object (value & colour doesn't matter)
		Tile t = new Tile("B", 1);
		
		// Check if the tile object exists
		assertNotNull(t);
	}
	
	public void testTileValues() {
		// Iterate from 1 to 13
		for (int i = 1; i < 14; i++) {
			// Create tile objects with values from 1 to 13
			Tile t = new Tile("B", i);
			
			// Check if the tile object has a value of i
			assertEquals(i, t.getValue());
		}
	}
	
	public void testTileColours() {
		// Create tile objects with each colour
		Tile tB = new Tile("B", 1);
		Tile tO = new Tile("O", 1);
		Tile tG = new Tile("G", 1);
		Tile tR = new Tile("R", 1);
		Tile tR2 = new Tile(0,1);
		
		// Check if the tile object has a colour of Black
		assertEquals("Black", tB.getColour());
		
		// Check if the tile object has a colour of Orange
		assertEquals("Orange", tO.getColour());
		
		// Check if the tile object has a colour of Green
		assertEquals("Green", tG.getColour());
		
		// Check if the tile object has a colour of Red
		assertEquals("Red", tR.getColour());
		
		assertEquals("Red", tR2.getColour());
	}
}
