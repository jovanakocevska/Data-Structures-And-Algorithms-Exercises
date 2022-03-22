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

public class HashKumanovskiDijalekt {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, kumanovski, literaturen, zbor, rezultat = "";
		String[] pom;
		String[] pom2;
		int n, i;

		s = br.readLine();
		n = Integer.parseInt(s);

		CBHT<String, String> tabela = new CBHT<String, String>(2 * n + 1);
		SLLNode<MapEntry<String, String>> tmp = null;

		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			kumanovski = pom[0];
			literaturen = pom[1];
			tabela.insert(kumanovski, literaturen);
		}

		s = br.readLine();
		pom2 = s.split(" ");

		for (i = 0; i < pom2.length; i++) {
			zbor = pom2[i];
			if (zbor.endsWith(",") || zbor.endsWith(".") || zbor.endsWith("?") || zbor.endsWith("!")) {
				zbor = zbor.substring(0, zbor.length() - 1);
			}
			zbor = zbor.toLowerCase();
			tmp = tabela.search(zbor);
			if (tmp == null) {
				rezultat += pom2[i] + " ";
			} else {
				zbor = tmp.element.value;

				if (Character.isUpperCase(pom2[i].charAt(0))) {
					zbor = Character.toUpperCase(zbor.charAt(0)) + zbor.substring(1);
				}

				if (pom2[i].endsWith(",") || pom2[i].endsWith(".") || pom2[i].endsWith("?") || pom2[i].endsWith("!")) {
					zbor += pom2[i].charAt(pom2[i].length() - 1);
					// zbor+=pom2[i].substring(pom2[i].length()-1);
				}

				rezultat += zbor + " ";
			}
		}

		System.out.println(rezultat);

	}

}
