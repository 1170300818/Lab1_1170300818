/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class MySocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	
    	Map<String, Set<String>> map=new HashMap<String,Set<String>>();
        String pattern = "(^|[^1-9a-z-_])@([a-z1-9_-]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m=null;
        for(int i=0;i<tweets.size();i++) {
   		    map.put(tweets.get(i).getAuthor().toLowerCase(), new TreeSet<>());
        	 m = r.matcher(tweets.get(i).getText().toLowerCase());
        	 while(m.find( )) {		
        		 String user_name = m.group(2).toLowerCase();
        	   	map.get(tweets.get(i).getAuthor().toLowerCase()).add(user_name);                    
                 }
               }
        int [][]connect=new int[map.size()][map.size()];
        Map<String, Integer> numMap=new HashMap<String,Integer>();
        Map<Integer, String> nameMap=new HashMap<Integer,String>();
        int i=0;
        for(String key:map.keySet()) {
        	numMap.put(key, i);
        	nameMap.put(i, key);
        	i++;
        }
        for(String key:map.keySet()) {
        	for(String value:map.get(key)) {
        		connect[numMap.get(key)][numMap.get(value)]=1;
        	}
        }
        int col = 0;
        int line = 0;
        int temp = 0;

        for (col = 0;col < map.size();col++) {
            for (line = 0;line < map.size();line++) {
                if (connect[line][col] != 0) {
                    for (temp = 0;temp < map.size();temp++) {
                        connect[line][temp] = connect[line][temp] | connect[col][temp];
                    }
                }
            }
        }
        for(int j=0;j<map.size();j++) {
        	for(int k=0;k<=j;k++) {
        		if(connect[j][k]==1&&connect[k][j]==1) {
        			if(!map.get(nameMap.get(j)).contains(nameMap.get(k))) {
        				map.get(nameMap.get(j)).add(nameMap.get(k));
        			}
        			if(!map.get(nameMap.get(k)).contains(nameMap.get(j))) {
        				map.get(nameMap.get(k)).add(nameMap.get(j));
        			}
        		}
        	}
        }
        return map;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        
    	 Map<String, Integer> name = new HashMap<>();
         int i = 0, cnt = 0;
         List<String> namelist = new ArrayList<>();
         List<String> list = new ArrayList<>();
         for(String s: followsGraph.keySet()) {
         	for(String k: followsGraph.get(s)) {
         			namelist.add(k);
         	}
         }
         for(i = 0; i < namelist.size(); ++i) {
         	if(name.containsKey(namelist.get(i))) {
         		name.put(namelist.get(i), name.get(namelist.get(i))+1);
         	}
         	else {
         		name.put(namelist.get(i), 1);
         	}
         }
        
     String temp = new String();
     while(!name.isEmpty()) {
     	cnt = 0;
     	for(String s: name.keySet()) {
     		if(name.get(s) > cnt) {
     			temp = s;
     			cnt = name.get(s);
     		}
     	}
     	list.add(temp);
     	name.remove(temp);
     }
     return list;
     }
    
}

