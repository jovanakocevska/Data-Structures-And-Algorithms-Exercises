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

class CBHT<K extends Comparable<K>, E> {

	// An object of class CBHT is a closed-bucket hash table, containing
	// entries of class MapEntry.
	private SLLNode<MapEntry<K,E>>[] buckets;

	@SuppressWarnings("unchecked")
	public CBHT(int m) {
		// Construct an empty CBHT with m buckets.
		buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
	}

	private int hash(K key) {
		// Translate key to an index of the array buckets.
		return Math.abs(key.hashCode()) % buckets.length;
	}

	public SLLNode<MapEntry<K,E>> search(K targetKey) {
		// Find which if any node of this CBHT contains an entry whose key is
		// equal
		// to targetKey. Return a link to that node (or null if there is none).
		int b = hash(targetKey);
		for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
				return curr;
		}
		return null;
	}

	public void insert(K key, E val) {		// Insert the entry <key, val> into this CBHT.
		MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
		int b = hash(key);
		for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (key.equals(((MapEntry<K, E>) curr.element).key)) {
				// Make newEntry replace the existing entry ...
				curr.element = newEntry;
				return;
			}
		}
		// Insert newEntry at the front of the 1WLL in bucket b ...
		buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
	}

	public void delete(K key) {
		// Delete the entry (if any) whose key is equal to key from this CBHT.
		int b = hash(key);
		for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
			if (key.equals(((MapEntry<K,E>) curr.element).key)) {
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
			for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
				temp += curr.element.toString() + " ";
			}
			temp += "\n";
		}
		return temp;
	}

}

public class HashSistemZaPrimanjePoraki {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, imeDatumVreme, kategorija, komandaVlez, imeDatumVremeVlez, brojacTabela;
		String []pom, komanda, nizaZaBrojac;
		int n, i, brojac = 0;
		
		s = br.readLine();
		n = Integer.parseInt(s);
		
		CBHT<String, String> tabela = new CBHT<String, String>(2*n+1);
		SLLNode<MapEntry<String, String>> tmp = null;
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" "); 
			imeDatumVreme = pom[0] + " " + pom[1] + " " + pom[2];
			kategorija = pom[3];
			tabela.insert(imeDatumVreme, kategorija + " " + "0");
		}
	
		s = br.readLine();
		n = Integer.parseInt(s);
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			komanda = pom[0].split("_");
			komandaVlez = komanda[0];
			imeDatumVremeVlez = pom[1] + " " + pom[2] + " " + pom[3];
			tmp = tabela.search(imeDatumVremeVlez);
			if (tmp != null) {
				nizaZaBrojac = tmp.element.value.split(" ");
				brojacTabela = nizaZaBrojac[1];
				brojac = Integer.parseInt(brojacTabela) + 1;
				if (komandaVlez.equals("READ")) {
					tmp.element.value = "READ " + brojac;
				} else if (komandaVlez.equals("UNREAD")) {
					tmp.element.value = "UNREAD " + brojac;
				} else if (komandaVlez.equals("DELETE")) {
					tmp.element.value = "TRASH " + brojac;
				} else { 
					tmp.element.value = "READ " + brojac;
				}
			}
		}
		
		s = br.readLine();
		n = Integer.parseInt(s);
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			tmp = tabela.search(s);
			if (tmp == null) {
				System.out.println("Nema takva poraka");
			} else {
				System.out.println(tmp.element.value);
			}
		}
	}
/*
VLEZ1:
3
MESSAGE1 15.01.2020 17:15 READ
MESSAGE2 15.01.2020 18:15 READ
MESSAGE1 15.01.2020 18:15 READ
2
UNREAD_MESSAGE MESSAGE1 15.01.2020 17:15
DELETE_MESSAGE MESSAGE1 15.01.2020 18:15
2
MESSAGE1 15.01.2020 17:15
MESSAGE1 15.01.2020 18:15

IZLEZ1:
UNREAD 1
TRASH 1



VLEZ2:
5
Exam 16.01.2020 17:17 UNREAD
Problem 16.01.2020 17:18 TRASH
Exam 16.01.2020 17:20 READ
Problem 17.01.2020 17:18 TRASH
Holidays 20.01.2020 14:20 READ
5
READ_MESSAGE Exam 16.01.2020 17:17
UNREAD_MESSAGE Exam 16.01.2020 17:17
RESTORE_MESSAGE Problem 16.01.2020 17:18
DELETE_MESSAGE Exam 16.01.2020 17:20
READ_MESSAGE Exam 16.01.2020 17:17
3
Problem 16.01.2020 17:18
Exam 16.01.2020 17:17
Holidays 20.01.2020 14:20

IZLEZ2:
READ 1
READ 3
READ 0



VLEZ3:
4
MESSAGE1 15.01.2020 17:15 UNREAD
MESSAGE2 15.01.2020 18:15 UNREAD
MESSAGE1 15.01.2020 18:15 UNREAD
MESSAGE3 19.01.2020 18:15 UNREAD
7
READ_MESSAGE MESSAGE1 15.01.2020 17:15
DELETE_MESSAGE MESSAGE1 15.01.2020 17:15
DELETE_MESSAGE MESSAGE3 19.01.2020 18:15
RESTORE_MESSAGE MESSAGE1 15.01.2020 17:15
UNREAD_MESSAGE MESSAGE1 15.01.2020 17:15
READ_MESSAGE MESSAGE2 15.01.2020 18:15
UNREAD_MESSAGE MESSAGE2 15.01.2020 17:15
3
MESSAGE1 15.01.2020 17:15
MESSAGE3 19.01.2020 18:15
MESSAGE1 15.01.2020 18:15

IZLEZ3:
UNREAD 4
TRASH 1
UNREAD 0
 */
}
