/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import P4.twitter.SocialNetwork;
import P4.twitter.Tweet;

public class MySocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
	private static final Instant P1 = Instant.parse("2019-03-13T19:00:00Z");
    private static final Instant P2 = Instant.parse("2019-03-13T20:00:00Z");
    private static final Instant P3 = Instant.parse("2019-03-13T21:00:00Z");
    private static final Instant P4 = Instant.parse("2019-03-13T22:30:00Z");
    private static final Tweet tweet1 = new Tweet(1, "aquareman", "We still got a lot to do, rigght? But why are you not here?.@batman", P1);
    private static final Tweet tweet2 = new Tweet(2, "batman", "@aquareman @lex I HAVE some PRIVATE business. Sorry. #hype", P2);
    private static final Tweet tweet3 = new Tweet(3,"lex","RT @batman: OK,got it",P3);
    private static final Tweet tweet4 = new Tweet(4,"Blondie","RT @batman: Glad to you see your boys returning to good relationship",P4);
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2,tweet3,tweet4));
        assertFalse("expected empty graph", followsGraph.isEmpty());
        assertTrue(followsGraph.get("aquareman").contains("batman"));
        assertTrue(followsGraph.get("batman").contains("aquareman"));
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph =  SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2,tweet3,tweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertFalse( influencers.isEmpty());
        assertEquals("batman", influencers.get(0));
        
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
