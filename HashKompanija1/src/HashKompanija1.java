import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

public class HashKompanija1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, vraboten, menadzer;
		String[] pom;
		int i, n, h1, h2, brojac;

		s = br.readLine();
		n = Integer.parseInt(s);

		OBHT<String, String> tabelaVM = new OBHT<String, String>(2 * n + 1);
		OBHT<String, Integer> tabelaM = new OBHT<String, Integer>(2 * n + 1);

		ArrayList<String> alMenadzer = new ArrayList<String>();

		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			vraboten = pom[0];
			menadzer = pom[1];
			h1 = tabelaVM.search(vraboten);
			if (h1 == -1) {
				tabelaVM.insert(vraboten, menadzer);
				if (!vraboten.equals(menadzer)) {
					h2 = tabelaM.search(menadzer);
					if (h2 == -1) {
						tabelaM.insert(menadzer, 1);
					} else {
						brojac = tabelaM.buckets[h2].value;
						brojac++;
						tabelaM.insert(menadzer, brojac);
					}
				}
			}
			if (!alMenadzer.contains(menadzer)) {
				alMenadzer.add(menadzer);
			}
		}
		
		String rez = "{";
		for (i = 0; i < alMenadzer.size(); i++) {
			menadzer = alMenadzer.get(i);
			h1 = tabelaVM.search(menadzer);
			if (h1 != -1) {				
				h2 = tabelaM.search(menadzer);
				if (h2 != -1) {
					rez += menadzer + "=";
					rez += tabelaM.buckets[h2].value + ",";
				}
			}
		}
		rez = rez.substring(0, rez.length() - 1);
		rez += "}";
		System.out.println(rez);
	}

	/*
16
Aleks Marko
Beti Marko
Marko Filip
Darko Elena
Elena Filip
Filip Luka
Natasha Luka
Petar Luka
Luka Luka
Petar2 Elena
Aleks Elena
Beti Elena
Elena Marija
Luka Marija
Petar Marija
Marija Marija
{Marko=2,Filip=2,Elena=2,Luka=3,Marija}
	 */
	
}
