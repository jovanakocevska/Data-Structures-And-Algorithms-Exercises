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
	public SLLNode<MapEntry<K, E>>[] buckets;

	@SuppressWarnings("unchecked")
	public CBHT(int m) {
		// Construct an empty CBHT with m buckets.
		buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
	}

	public int hash(K key) {
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

class Ime implements Comparable<Ime> {
	String ime;

	public Ime(String ime) {
		super();
		this.ime = ime;
	}

	@Override
	public int compareTo(Ime o) {
		// TODO Auto-generated method stub
		return ime.compareTo(o.ime);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (100 * ime.charAt(0) + ime.charAt(1)) % 9091;
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
		Ime other = (Ime) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		return true;
	}
}

public class HashZavodZaStatistika {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, ime, pol;
		int i, n, brojac;
		String []pom;
		Ime im;
		
		s = br.readLine();
		n = Integer.parseInt(s);
		
		CBHT<Ime, Integer> tabelaZenski = new CBHT<Ime, Integer>(2*n+1);
		CBHT<Ime, Integer> tabelaMaski = new CBHT<Ime, Integer>(2*n+1);
		SLLNode<MapEntry<Ime,Integer>> tmp = null;
		
		for(i = 0; i < n; i++) {
			s = br.readLine(); // .toUpperCase();
			pom = s.split(" ");
			ime = pom[0].toUpperCase();
			pol = pom[1];
			im = new Ime(ime);
			if (pol.equals("F")) {
				tmp = tabelaZenski.search(im);
				if (tmp == null) {
					brojac = 1;
					tabelaZenski.insert(im, brojac);
				} else {
					brojac = tmp.element.value;
					brojac++;
					tabelaZenski.insert(im, brojac);
				}
			} else {
				tmp = tabelaMaski.search(im);
				if (tmp == null) {
					brojac = 1;
					tabelaMaski.insert(im, brojac);
				} else {
					brojac = tmp.element.value;
					brojac++;
					tabelaMaski.insert(im, brojac);
				}
			}
		}
	
		pol = br.readLine();
		if(pol.equals("F")) {
			while (true) {
				ime = br.readLine().toUpperCase();
				if(ime.equals("KRAJ")) {
					break;
				}
				im = new Ime(ime);
				tmp = tabelaZenski.search(im);
				if(tmp == null) {
					System.out.println("Nema takvo ime");
				} else {
					String iminja = "";
					int h = tabelaZenski.hash(im);
					SLLNode<MapEntry<Ime, Integer>> slicni = null;
					for (slicni = tabelaZenski.buckets[h]; slicni != null; slicni = slicni.succ) {
						if (!ime.equals(slicni.element.key.ime))
							iminja += slicni.element.key.ime + " ";
					}
					if (iminja.equals("")) System.out.println("Nema");
					else System.out.println(iminja.trim());
					System.out.println(ime + " " + pol + " " + tmp.element.value);
				}
			}
		} else {
			while (true) {
				ime = br.readLine().toUpperCase();
				if(ime.equals("KRAJ")) {
					break;
				}
				im = new Ime(ime);
				tmp = tabelaMaski.search(im);
				if(tmp == null) {
					System.out.println("Nema takvo ime");
				} else {
					String iminja = "";
					int h = tabelaMaski.hash(im);
					SLLNode<MapEntry<Ime, Integer>> slicni;
					slicni = tabelaMaski.buckets[h];
					while (slicni != null) {
						if (!ime.equals(slicni.element.key.ime))
							iminja += slicni.element.key.ime + " ";
						slicni = slicni.succ;
					}
					if (iminja.equals("")) System.out.println("Nema");
					else System.out.println(iminja.trim());
					System.out.println(ime + " " + pol + " " + tmp.element.value);
				}
			}
		}		
	}
	

}
