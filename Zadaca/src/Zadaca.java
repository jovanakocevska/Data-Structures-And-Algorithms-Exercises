import java.io.*;
import java.util.*;

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

	public Object getElement() {
		// TODO Auto-generated method stub
		return null;
	}
}

class MapEntry<K extends Comparable<K>, E> implements Comparable<K> {

	// Each MapEntry object is a pair consisting of a key (a Comparable
	// object) and a value (an arbitrary object).
	K key;
	E value;

	public MapEntry(K key, E value) {
		this.key = key;
		this.value = value;
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
		// for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr =
		// curr.succ) {
		// if (key.equals(((MapEntry<K, E>) curr.element).key)) {
		// Make newEntry replace the existing entry ...
		// curr.element = newEntry;
		// return;
		// }
		// }
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

public class Zadaca {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Vnesete broj na vraboteni");
		int N = Integer.parseInt(br.readLine());

		CBHT<String, String> tabela = new CBHT<String, String>(2 * N);
		//System.out.println("Vnesete gi podatocite za vrabotenite - IME - DD/MM/YYYY");
		for (int i = 0; i < N; i++) {
			String line = br.readLine();

			String[] niza = line.split(" ");
			String ime = niza[0] + " " + niza[1];
			String datum[] = niza[2].split("/");
			String rodenden = datum[0] + " " + datum[1];

			ime = ime + " " + datum[2];

			tabela.insert(rodenden, ime);
		}
		
		System.out.println(tabela);

		//System.out.println("Datum: ");

		String line = br.readLine();
		String dm[] = line.split("/");
		int godina = Integer.parseInt(dm[2]);
		String datum = dm[0] + " " + dm[1];

		SLLNode<MapEntry<String, String>> node1 = tabela.search(datum);
		
		if (node1 != null) {
			String dobiGodina[] = node1.element.value.split(" ");
			for (int i = 0; i < dobiGodina.length; i++) {
				String imeVraboten = dobiGodina[0] + " " + dobiGodina[1];
				int godinaRoden = Integer.parseInt(dobiGodina[2]);
				int brojGodini = godina - godinaRoden;
				String rez = imeVraboten + " " + brojGodini;
				System.out.println(rez);
			}
		} else {
			System.out.println("Nema");
		}

	}

}
