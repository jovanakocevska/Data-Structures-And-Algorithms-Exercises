import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable 
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }
    
    public int compareTo (K that) {
    // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
		MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "<" + key + "," + value + ">";
    }
}

class OBHT<K extends Comparable<K>,E> {

    // An object of class OBHT is an open-bucket hash table, containing entries 
    // of class MapEntry.
    private MapEntry<K,E>[] buckets;
    
    // buckets[b] is null if bucket b has never been occupied. 
    // buckets[b] is former if bucket b is formerly-occupied 
    // by an entry that has since been deleted (and not yet replaced).

    static final int NONE = -1; // ... distinct from any bucket index.
    
    private static final MapEntry former = new MapEntry(null, null);
    // This guarantees that, for any genuine entry e, 
    // e.key.equals(former.key) returns false.
    
    private int occupancy = 0;
    // ... number of occupied or formerly-occupied buckets in this OBHT.
    
    @SuppressWarnings("unchecked")
	public OBHT (int m) {
    // Construct an empty OBHT with m buckets.
        buckets = (MapEntry<K,E>[]) new MapEntry[m];
    }


    private int hash (K key) {
    // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    
    public int search (K targetKey) {
    // Find which if any bucket of this OBHT is occupied by an entry whose key
    // is equal to targetKey. Return the index of that bucket.
        int b = hash(targetKey); int n_search=0;
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            if (oldEntry == null)
                return NONE;
            else if (targetKey.equals(oldEntry.key))
                return b;
            else
            {
                b = (b + 1) % buckets.length;
                n_search++;
      	        if(n_search==buckets.length)
                    return NONE;

            }
        }
    }


    public void insert (K key, E val) {
    // Insert the entry <key, val> into this OBHT.
        MapEntry<K,E> newEntry = new MapEntry<K,E>(key, val);
        int b = hash(key); int n_search=0;
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            if (oldEntry == null) {
                if (++occupancy == buckets.length) {
                    System.out.println("Hash tabelata e polna!!!");
                }
                buckets[b] = newEntry;
                return;
            }
            else if (oldEntry == former
                    || key.equals(oldEntry.key)) {
                buckets[b] = newEntry;
                return;
            } 
            else
            {
                b = (b + 1) % buckets.length;
                n_search++;
      	        if(n_search==buckets.length)
                    return;

            }
        }
    }

    
    @SuppressWarnings("unchecked")
	public void delete (K key) {
    // Delete the entry (if any) whose key is equal to key from this OBHT.
        int b = hash(key); int n_search=0;
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            
            if (oldEntry == null)
                return;
            else if (key.equals(oldEntry.key)) {
                buckets[b] = former;//(MapEntry<K,E>)former;
                return;
            } 
            else{
                b = (b + 1) % buckets.length;
                n_search++;
      	        if(n_search==buckets.length)
                    return;

            }
        }
    }


    public String toString () {
    	String temp = "";
    	for (int i = 0; i < buckets.length; i++) {
    	    temp += i + ":";
    	    if (buckets[i] == null)
    	        temp += "\n";
    	    else if (buckets[i] == former)
    	        temp += "former\n";
    	    else
    	        temp += buckets[i] + "\n";
    	}
    	return temp;
    }


    public OBHT<K,E> clone () {
    	OBHT<K,E> copy = new OBHT<K,E>(buckets.length);
    	for (int i = 0; i < buckets.length; i++) {
    	    MapEntry<K,E> e = buckets[i];
    	    if (e != null && e != former)
    	        copy.buckets[i] = new MapEntry<K,E>(e.key, e.value);
    	    else
    	        copy.buckets[i] = e;
    	}
    	return copy;
    }
}

public class HashKtiPozitivenBrojStoFali {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		String pom[];
		int i, h, k, brojac = 0;
		
		// s = br.readLine(); 
		// pom = s.split(","); 
		
		s = br.readLine();
		k = Integer.parseInt(s);
		
		// OBHT<Integer, Integer> tabela = new OBHT<Integer, Integer>(pom.length*2-1);
		
		OBHT<Integer, Integer> tabela = new OBHT<Integer, Integer>(1000);
		
		while (true) {
			s = br.readLine();
			if (s.equals("#")) {
				break;
			}
			tabela.insert(Integer.parseInt(s), Integer.parseInt(s));
		}
		
		/*
		for (i = 0; i < pom.length; i++) {
			tabela.insert(Integer.parseInt(pom[i]), Integer.parseInt(pom[i]));
		}
		*/
		
		for (i = 1; i <= 100; i ++) {
			h = tabela.search(i);
			if (h == -1) {
				brojac++;
				if (brojac == k) {
					System.out.println(k + "-tiot broj sto fali e " + i);
				}
			} else {
				continue;
			}
		}

	}
/*
Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
Find the kth positive integer that is missing from this array.

Example 1:
Input: arr = [2,3,4,7,11], k = 5
2,3,4,7,11
5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. 
The 5th missing positive integer is 9.

Example 2:
Input: arr = [1,2,3,4], k = 2
1,2,3,4
2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. 
The 2nd missing positive integer is 6.
 */
}
