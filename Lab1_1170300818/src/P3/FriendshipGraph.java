package P3;

import java.util.*;

import P3.Person;

public class FriendshipGraph {
	List<Person> person=new ArrayList<>();
	Map<Person, Set<Person>> rela=new HashMap<Person, Set<Person>>();
	List<Integer> dist=new ArrayList<Integer>();
	void addVertex(Person st){
		person.add(st);
	}
	void addEdge (Person a,Person b) {
		if(!rela.containsKey(a)) {
			Set<Person> friends=new HashSet<Person>();
			friends.add(b);
			rela.put(a, friends);
			}
			else {
				Set<Person> friends=rela.get(a);
				friends.add(b);
				rela.put(a, friends);
			}
		}
	boolean contain(Person a) {
		if (rela.get(a)!=null)
			return true;
		return false;
	}
	int getDistance (Person a,Person b) {
		if (a==b) {
			return 0;
		}
		Queue<Person> list = new LinkedList<Person>();
		Map<Person, Boolean> visited=new HashMap<Person, Boolean>();
		int count3=0;
		list.add(a);
		while (!list.isEmpty()) {
			Person x = list.poll();
			visited.put(x, true);
			Iterator<Person> count2 =rela.get(x).iterator();
			count3 ++;
			while(count2.hasNext()) {
				Person y =count2.next();
				if (y==b) {
					return count3;
				}
				else {
					if(!visited.containsKey(y))
						list.add(y);
				}
			}
		}
		return -1;
	}



public static void main(String[] args) {
	FriendshipGraph graph = new FriendshipGraph();
	 Person rachel = new Person("Rachel");
	 Person ross = new Person("Ross");
	 Person ben = new Person("Ben");
	 Person kramer = new Person("Kramer");
	 graph.addVertex(rachel);
	 graph.addVertex(ross);
	 graph.addVertex(ben);
	 
	 graph.addVertex(kramer);
	 graph.addEdge(rachel, ross);
	 graph.addEdge(ross, rachel);
	 graph.addEdge(ross, ben);
	 graph.addEdge(ben, ross);
	
	 System.out.println(graph.getDistance(rachel,ross));
	 //should print 1
	 System.out.println(graph.getDistance(rachel,ben));
	 //should print 2
	 System.out.println(graph.getDistance(rachel,rachel));
	 //should print 0
	 System.out.println(graph.getDistance(rachel,kramer));
	 //should print -1
}
}

