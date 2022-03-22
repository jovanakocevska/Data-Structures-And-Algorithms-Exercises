import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

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

class Merenje {
	float vkZagaduvanje;
	int brMerenja;

	public Merenje(float vkZagaduvanje, int brMerenja) {
		super();
		this.vkZagaduvanje = vkZagaduvanje;
		this.brMerenja = brMerenja;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(vkZagaduvanje / brMerenja);
	}
}

public class HashOpshtini2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, imeOpstina;
		String[] pom;
		float zagaduvanje;
		int n, brojac = 0, i;
		Merenje m;

		s = br.readLine();
		n = Integer.parseInt(s);

		CBHT<String, Merenje> tabela = new CBHT<String, Merenje>(2 * n + 1);
		SLLNode<MapEntry<String, Merenje>> tmp;

		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			imeOpstina = pom[0];
			zagaduvanje = Float.parseFloat(pom[1]);
			tmp = tabela.search(imeOpstina);
			if (tmp == null) {
				brojac = 1;
				m = new Merenje(zagaduvanje, brojac);
				tabela.insert(imeOpstina, m);
			} else {
				m = tmp.element.value;
				m.vkZagaduvanje += zagaduvanje;
				m.brMerenja++;
				tabela.insert(imeOpstina, m);
			}
		}

		imeOpstina = br.readLine();
		tmp = tabela.search(imeOpstina);
		if (tmp == null) {
			System.out.println("Nema merenje za dadenata opstina");
		} else {
			m = tmp.element.value;
			System.out.println(m);
			zagaduvanje = m.vkZagaduvanje / m.brMerenja;

			System.out.printf("%.2f\n", zagaduvanje);

			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println(df.format(zagaduvanje));

			zagaduvanje = (float) Math.round(zagaduvanje * 100) / 100;
			System.out.println(zagaduvanje);
		}

	}

}
