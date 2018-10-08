import junit.framework.TestCase;

public class HandTest extends TestCase {
	public void testHandExists() {
		Hand hand = new Hand();
		assertNotNull(hand);
	}
	
	public void testNumTiles() {
		Hand hand = new Hand();
		assertEquals(14, hand.getNumTiles());
	}
}
