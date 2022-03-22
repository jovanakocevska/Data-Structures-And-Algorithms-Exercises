import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class SLLNode<E> {
	protected E element;
	protected SLLNode<E> succ;

	public SLLNode(E elem, SLLNode<E> succ) {
		this.element = elem;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}

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

class CBHT<K extends Comparable<K>, E> {

	// An object of class CBHT is a closed-bucket hash table, containing
	// entries of class MapEntry.
	private SLLNode<MapEntry<K, E>>[] buckets;

	@SuppressWarnings("unchecked")
	public CBHT(int m) {
		// Construct an empty CBHT with m buckets.
		buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
	}

	private int hash(K key) {
		// Translate key to an index of the array buckets.
		return Math.abs(key.hashCode()) % buckets.length;
	}

	public SLLNode<MapEntry<K, E>> search(K targetKey) {
		// Find which if any node of this CBHT contains an entry whose key is
		// equal
		// to targetKey. Return a link to that node (or null if there is none).
		int b = hash(targetKey);
		for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
				return curr;
		}
		return null;
	}

	public void insert(K key, E val) { // Insert the entry <key, val> into this CBHT.
		MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
		int b = hash(key);
		for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (key.equals(((MapEntry<K, E>) curr.element).key)) {
				// Make newEntry replace the existing entry ...
				curr.element = newEntry;
				return;
			}
		}
		// Insert newEntry at the front of the 1WLL in bucket b ...
		buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
	}

	public void delete(K key) {
		// Delete the entry (if any) whose key is equal to key from this CBHT.
		int b = hash(key);
		for (SLLNode<MapEntry<K, E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
			if (key.equals(((MapEntry<K, E>) curr.element).key)) {
				if (pred == null)
					buckets[b] = curr.succ;
				else
					pred.succ = curr.succ;
				return;
			}
		}
	}

	public String toString() {
		String temp = "";
		for (int i = 0; i < buckets.length; i++) {
			temp += i + ":";
			for (SLLNode<MapEntry<K, E>> curr = buckets[i]; curr != null; curr = curr.succ) {
				temp += curr.element.toString() + " ";
			}
			temp += "\n";
		}
		return temp;
	}

}

class Lek implements Comparable<Lek> {
	String imeLek;
	int pozneg;
	int cena;
	int kolicina;

	public Lek(String imeLek, int pozneg, int cena, int kolicina) {
		super();
		this.imeLek = imeLek; // .toUpperCase();
		this.pozneg = pozneg;
		this.cena = cena;
		this.kolicina = kolicina;
	}

	@Override
	public int compareTo(Lek o) {
		// TODO Auto-generated method stub
		return imeLek.compareTo(o.imeLek);
	}

	@Override
	public int hashCode() {
		int result;
		result = (29 * (29 * (29 * 0 + imeLek.charAt(0)) + imeLek.charAt(1)) + imeLek.charAt(2)) % 102780;
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
		Lek other = (Lek) obj;
		if (imeLek == null) {
			if (other.imeLek != null)
				return false;
		} else if (!imeLek.equals(other.imeLek))
			return false;
		return true;
	}

	public String toString() {
		String s = imeLek + "\n";
		if (pozneg == 1) {
			s += "POZ\n";
		} else {
			s += "NEG\n";
		}
		// s += pozneg == 1 ? "POZ\n" : "NEG\n";
		s += cena + "\n";
		s += kolicina + "\n";

		return s;
	}

}

public class HashApteka {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, imeLek;
		int i, n, pozneg, cena, kolicina;
		String[] pom;
		Lek l;

		s = br.readLine();
		n = Integer.parseInt(s);

		CBHT<Lek, Lek> tabela = new CBHT<Lek, Lek>(2 * n + 1);
		SLLNode<MapEntry<Lek, Lek>> tmp;

		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			imeLek = pom[0].toUpperCase(); // tuka toUpper
			pozneg = Integer.parseInt(pom[1]);
			cena = Integer.parseInt(pom[2]);
			kolicina = Integer.parseInt(pom[3]);
			l = new Lek(imeLek, pozneg, cena, kolicina);
			tabela.insert(l, l);
		}

		while (true) {
			imeLek = br.readLine(); // moze tuka .toUpper(); namesto dole vo l
			if (imeLek.equals("KRAJ")) {
				break;
			}
			s = br.readLine();
			kolicina = Integer.parseInt(s);
			l = new Lek(imeLek.toUpperCase(), 0, 0, kolicina); // tuka toUpper
			tmp = tabela.search(l);
			if (tmp == null) {
				System.out.println("Nema takov lek");
			} else { // pecatenjeto moze da se napravi vo toString
				Lek najden = tmp.element.value;
				// System.out.println(najden);
				System.out.println(najden.imeLek);
				if (najden.pozneg == 1) { // System.out.println(najden.pozneg == 1 ? "POZ\n" : "NEG\n");
					System.out.println("POZ");
				} else
					System.out.println("NEG");
				System.out.println(najden.cena);
				System.out.println(najden.kolicina);
				if (najden.kolicina >= kolicina) {
					najden.kolicina -= kolicina;
					tabela.insert(najden, najden);
					System.out.println("Napravena naracka");
				} else {
					System.out.println("Nema dovolno lekovi");
				}
			}
		}

	}

}
