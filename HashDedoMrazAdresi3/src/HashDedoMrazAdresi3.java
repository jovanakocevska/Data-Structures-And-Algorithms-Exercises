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

class Dete implements Comparable<Dete> {
	String dete;

	public Dete(String dete) {
		super();
		this.dete = dete;
	}

	@Override
	public int compareTo(Dete arg0) {
		// TODO Auto-generated method stub
		return dete.compareTo(arg0.dete);
	}

	@Override
	public int hashCode() {
		int result = (2000 * dete.charAt(0) + dete.charAt(1)) / 1800;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dete other = (Dete) obj;
		if (dete == null) {
			if (other.dete != null)
				return false;
		} else if (!dete.equals(other.dete))
			return false;
		return true;
	}
}

class Adresa {
	String ulica;
	int broj;
	String grad;
	String drzava;

	public Adresa(String ulica, int broj, String grad, String drzava) {
		super();
		this.ulica = ulica;
		this.broj = broj;
		this.grad = grad;
		this.drzava = drzava;
	}
}

public class HashDedoMrazAdresi3 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, dete, novaAdresa, staraAdresa, a;
		String[] pom;
		int n, i, h1, h2, broj;
		String ulica, grad, drzava;
		Dete de;
		Adresa adr;

		s = br.readLine();
		n = Integer.parseInt(s);

		OBHT<Dete, Adresa> tabela1 = new OBHT<Dete, Adresa>(2 * n + 1);

		for (i = 0; i < n; i++) {
			s = br.readLine();
			dete = s;
			de = new Dete(dete);
			s = br.readLine();
			pom = s.split(" ");
			ulica = pom[0];
			broj = Integer.parseInt(pom[1]);
			grad = pom[2];
			drzava = pom[3];
			adr = new Adresa(ulica, broj, grad, drzava);
			tabela1.insert(de, adr);
		}

		s = br.readLine();
		n = Integer.parseInt(s);

		OBHT<String, String> tabela2 = new OBHT<String, String>(2 * n + 1);

		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			staraAdresa = pom[0];
			novaAdresa = pom[1];
			tabela2.insert(staraAdresa, novaAdresa);
		}

		s = br.readLine();
		de = new Dete(s);
		h1 = tabela1.search(de);
		if (h1 == -1) {
			System.out.println("Nema poklon");
		} else {
			a = tabela1.buckets[h1].value.ulica;
			Adresa add = tabela1.buckets[h1].value;
			h2 = tabela2.search(a);
			if (h2 != -1) {
				System.out.print(tabela2.buckets[h2].value + " " + add.broj + " " + add.grad + " " + add.drzava);
			} else {
				System.out.print(add.ulica + " " + add.broj + " " + add.grad + " " + add.drzava);
			}
		}
	}

}
