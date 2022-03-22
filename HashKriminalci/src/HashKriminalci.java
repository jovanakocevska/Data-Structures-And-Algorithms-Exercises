import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class MapEntry<K extends Comparable<K>, E> implements Comparable<K> {

	// Each MapEntry object is a pair consisting of a key (a Comparable
	// object) and a value (an arbitrary object).
	K key;
	E value;

	public MapEntry(K key, E val) {
		this.key = key;
		this.value = val;
	}

	public int compareTo(K that) {
		// Compare this map entry to that map entry.
		@SuppressWarnings("unchecked")
		MapEntry<K, E> other = (MapEntry<K, E>) that;
		return this.key.compareTo(other.key);
	}

	public String toString() {
		return "<" + key + "," + value + ">";
	}
}

class OBHT<K extends Comparable<K>, E> {

	// An object of class OBHT is an open-bucket hash table, containing entries
	// of class MapEntry.
	public MapEntry<K, E>[] buckets;

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
	public OBHT(int m) {
		// Construct an empty OBHT with m buckets.
		buckets = (MapEntry<K, E>[]) new MapEntry[m];
	}

	private int hash(K key) {
		// Translate key to an index of the array buckets.
		return Math.abs(key.hashCode()) % buckets.length;
	}

	public int search(K targetKey) {
		// Find which if any bucket of this OBHT is occupied by an entry whose key
		// is equal to targetKey. Return the index of that bucket.
		int b = hash(targetKey);
		int n_search = 0;
		for (;;) {
			MapEntry<K, E> oldEntry = buckets[b];
			if (oldEntry == null)
				return NONE;
			else if (targetKey.equals(oldEntry.key))
				return b;
			else {
				b = (b + 1) % buckets.length;
				n_search++;
				if (n_search == buckets.length)
					return NONE;

			}
		}
	}

	public void insert(K key, E val) {
		// Insert the entry <key, val> into this OBHT.
		MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
		int b = hash(key);
		int n_search = 0;
		for (;;) {
			MapEntry<K, E> oldEntry = buckets[b];
			if (oldEntry == null) {
				if (++occupancy == buckets.length) {
					System.out.println("Hash tabelata e polna!!!");
				}
				buckets[b] = newEntry;
				return;
			} else if (oldEntry == former || key.equals(oldEntry.key)) {
				buckets[b] = newEntry;
				return;
			} else {
				b = (b + 1) % buckets.length;
				n_search++;
				if (n_search == buckets.length)
					return;

			}
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(K key) {
		// Delete the entry (if any) whose key is equal to key from this OBHT.
		int b = hash(key);
		int n_search = 0;
		for (;;) {
			MapEntry<K, E> oldEntry = buckets[b];

			if (oldEntry == null)
				return;
			else if (key.equals(oldEntry.key)) {
				buckets[b] = former;// (MapEntry<K,E>)former;
				return;
			} else {
				b = (b + 1) % buckets.length;
				n_search++;
				if (n_search == buckets.length)
					return;

			}
		}
	}

	public String toString() {
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

	public OBHT<K, E> clone() {
		OBHT<K, E> copy = new OBHT<K, E>(buckets.length);
		for (int i = 0; i < buckets.length; i++) {
			MapEntry<K, E> e = buckets[i];
			if (e != null && e != former)
				copy.buckets[i] = new MapEntry<K, E>(e.key, e.value);
			else
				copy.buckets[i] = e;
		}
		return copy;
	}
}

public class HashKriminalci {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, imePrezime, dnk1, dnk2, dnk, barajdnk1, barajdnk2, barajdnkkraen1, barajdnkkraen2;
		int n, i, h1, h2;
		
		s = br.readLine();
		n = Integer.parseInt(s);
		
		OBHT<String, String> tabela = new OBHT<String, String>(2*n+1);
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			imePrezime = s;
			s = br.readLine();
			dnk1 = s;
			s = br.readLine();
			dnk2 = s;
			dnk = dnk1 + ";" + dnk2;
			tabela.insert(dnk, imePrezime);
		}
		
		s = br.readLine();
		barajdnk1 = s;
		s = br.readLine();
		barajdnk2 = s;
		barajdnkkraen1 = barajdnk1 + ";" + barajdnk2;
		barajdnkkraen2 = barajdnk2 + ";" + barajdnk1;
		
		h1 = tabela.search(barajdnkkraen1);
		h2 = tabela.search(barajdnkkraen2);
		if (h1 != -1) {
			System.out.println(tabela.buckets[h1].value);
		} else if (h2 != -1) {
			System.out.println(tabela.buckets[h2].value);
		} else {
			System.out.println("Nesoodvetno");
		}
	
	}
/*
MVR poseduva baza na podatoci za odreden broj na kriminalci vo koja za sekoj kriminalec se cuvaat informacii za
negotovo ime i prezime i uste 2 sekvenci od negovata DNK.
Vasa zadaca e za dadeni dve sekvenci da proverite dali se sovpagjaat so dvete sekvenci na nekoj kriminalec.
Dokolku e pronajden takov kriminalec so dvete sekvenci da se ispecati negotovo ime i prezime.
Dokolku ne se sovpagjaat edna ili dve sekvenci togas da se ispecati "Nesoodvetno".

VLEZ1: 
2
MagdalenaKostoska
JKJKJLKSDSS
ASDSTTTOCCT
IgorKulev
BDDCCOTSDSA
OTTCCCOSDSD
ASDSTTTOCCT
JKJKJLKSDSS
IZLEZ1:
MagdalenaKostoska

VLEZ2:
2
MagdalenaKostoska
JKJKJLKSDSS
ASDSTTTOCCT
IgorKulev
BDDCCOTSDSA
OTTCCCOSDSD
JKJKJLKSDSS
KJFKAFNJAFF
IZLEZ2:
Nesoodvetno
	 */
	
}
