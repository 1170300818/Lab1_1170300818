package P3;

import static org.junit.Assert.*;

import org.junit.Test;
import P3.Person;
import P3.FriendshipGraph;
public class FriendshipGraphTest {
	
	FriendshipGraph graph = new FriendshipGraph();
	Person rachel = new Person("Rachel");
	Person ross = new Person("Ross");
	Person ben = new Person("Ben");
	Person kramer = new Person("Kramer");
	
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	
	@Test
	public void addVertexTest() {
		
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		assertEquals(kramer.GetName(),"Kramer");

	}
	@Test
	public void addEdgeTest() {
		graph.addEdge(ben,ross);
		graph.addEdge(rachel,ross);
		assertTrue(graph.contain(ben));
		assertTrue(graph.contain(rachel));
	}
	
	@Test
	public void getDistanceTest() {
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		assertEquals(1,graph.getDistance(rachel,ross));
		assertEquals(2,graph.getDistance(rachel,ben));
		assertEquals(0,graph.getDistance(rachel,rachel));
		assertEquals(-1,graph.getDistance(rachel,kramer));
	}
}
