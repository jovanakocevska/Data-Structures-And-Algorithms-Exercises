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

class Kosharkar {
	String imePrezime;
	String poeni;
	String skokovi;
	String asistencii;
	public Kosharkar(String imePrezime, String poeni, String skokovi, String asistencii) {
		super();
		this.imePrezime = imePrezime;
		this.poeni = poeni;
		this.skokovi = skokovi;
		this.asistencii = asistencii;
	}
}

public class HashNajdobarKosharkar2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, dres, imePrezime, poeni, skokovi, asistencii, kosharkar;
		String []pom;
		int n, i, h;
		Kosharkar k;
	
		s = br.readLine();
		n = Integer.parseInt(s);
		
		OBHT<String, Kosharkar> tabela = new OBHT<String, Kosharkar>(2*n+1);
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			dres = pom[0];
			imePrezime = pom[1];
			poeni = pom[2];
			skokovi = pom[3];
			asistencii = pom[4];
			h = tabela.search(dres);
			k = new Kosharkar (imePrezime, poeni, skokovi, asistencii);
			if (h != -1) {
				Kosharkar najden = tabela.buckets[h].value;
				if (Integer.parseInt(k.poeni) > Integer.parseInt(najden.poeni)) {
					tabela.insert(dres, k);
				} else if (Integer.parseInt(k.poeni) == Integer.parseInt(najden.poeni)) {
					if (Integer.parseInt(k.skokovi) > Integer.parseInt(najden.skokovi)) {
						tabela.insert(dres, k);
					} else if (Integer.parseInt(k.skokovi) == Integer.parseInt(najden.skokovi)) {
						if (Integer.parseInt(k.asistencii) > Integer.parseInt(najden.asistencii)) {
							tabela.insert(dres, k);
						} /*else if (Integer.parseInt(k.asistencii) == Integer.parseInt(najden.asistencii)) {
							continue;
						}*/
					}
				}
			} else {
				tabela.insert(dres, k);
			}
		}
		
		s = br.readLine();
		h = tabela.search(s);
		if (h != -1) {
			System.out.println(tabela.buckets[h].value.imePrezime);
		} else {
			System.out.println("Nema takov kosharkar");
		}
		
	}
	
	/*
VLEZ1:
10
3 Nekoj1 30 15 10
5 Nekoj2 50 20 10
3 Nekoj3 30 16 4
3 Nekoj4 50 10 10
10 Nekoj5 33 10 10
5 Nekoj6 70 10 10
3 Nekoj7 50 10 17
10 Nekoj8 40 10 10
5 Nekoj9 55 55 55
3 Nekoj10 15 10 10
3
IZLEZ1:
Nekoj7

VLEZ2:
10
3 Nekoj1 30 15 10
5 Nekoj2 50 20 10
3 Nekoj3 30 16 4
3 Nekoj4 50 10 10
10 Nekoj5 33 10 10
5 Nekoj6 70 10 10
3 Nekoj7 50 10 17
10 Nekoj8 40 10 10
5 Nekoj9 55 55 55
3 Nekoj10 15 10 10
5
IZLEZ2:
Nekoj6

VLEZ3:
10
3 Nekoj1 30 15 10
5 Nekoj2 50 20 10
3 Nekoj3 30 16 4
3 Nekoj4 50 10 10
10 Nekoj5 33 10 10
5 Nekoj6 70 10 10
3 Nekoj7 50 10 17
10 Nekoj8 40 10 10
5 Nekoj9 55 55 55
3 Nekoj10 15 10 10
15
IZLEZ3:
Nema takov kosharkar
	 */

}
