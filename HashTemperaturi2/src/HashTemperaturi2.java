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

class Podatoci {
	String saat1;
	String saat2;
	String temperatura;
	
	public Podatoci(String saat1, String saat2, String temperatura) {
		super();
		this.saat1 = saat1;
		this.saat2 = saat2;
		this.temperatura = temperatura;
	}
}

public class HashTemperaturi2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, grad, barajGrad;
		String saat1, saat2, tempVlez, t;
		String []pom;
		int n, i;
		Podatoci p;
		
		s = br.readLine();
		n = Integer.parseInt(s);
		
		CBHT<String, Podatoci> tabela = new CBHT<String, Podatoci>(2*n+1);
		SLLNode<MapEntry<String, Podatoci>> tmp = null;
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			grad = pom[0];
			saat1 = pom[1];
			saat2 = pom[2];
			tempVlez = pom[3];
			p = new Podatoci(saat1, saat2, tempVlez);
			tmp = tabela.search(grad);
			if (tmp == null) {
				tabela.insert(grad, p);
			} else {
				t = tmp.element.value.temperatura;
				if (Float.parseFloat(t) < Float.parseFloat(tempVlez)) {					
					tabela.insert(grad, p);
				}
			}
		}
		
		barajGrad = br.readLine();
		tmp = tabela.search(barajGrad);
		if (tmp == null) {
			System.out.println("Nema merenja za dadeniot grad");
		} else {
			p = tmp.element.value;
			System.out.println(tmp.element.key + " " + p.saat1 + "-" + 
					p.saat2 + " " + p.temperatura);
		}
		
	}
	
	/*
Хешот беше со дадени името на општината, временски интервал и температура.
Да се испечати влезот со највисоката температура за општината која се бара.
пример за влез:
4
Скопје 13:00 15:30 25.6
Прилеп 12:00 18:00 23.0
Битола 16:00 18:30 22.3
Прилеп 18:00 20:30 18.2
Прилеп 
Излезот ќе биде :
Прилеп 12:00-18:00 23.0

5
Скопје 13:00 15:30 25.6
Прилеп 12:00 18:00 23.0
Битола 16:00 18:30 22.3
Прилеп 18:00 20:30 18.2
Прилеп 15:00 18:30 28.2
Прилеп
Излезот ќе биде :
Прилеп 15:00-18:30 28.2
	 */

}
