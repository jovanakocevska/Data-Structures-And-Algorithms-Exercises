import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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

public class HashRodendeni {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s, ime, prezime, rodenden, imePrezimeGodina, denMesec, denMesecBaraj, godina, imePrezimeNovo, godinaBaraj;
		String imePrezime, rezultat;
		String []pom;
		String []pom2;
		String []pom3;
		String []pom4;
		String []pom5;
		int n, i, h, godinaRezultat;
		
		s = br.readLine();
		n = Integer.parseInt(s);
		
		OBHT<String, String> tabela = new OBHT<String, String>(2*n+1);
		
		for (i = 0; i < n; i++) {
			s = br.readLine();
			pom = s.split(" ");
			ime = pom[0];
			prezime = pom[1];
			rodenden = pom[2];
			
			pom2 = rodenden.split("/");
			denMesec = pom2[0] + pom2[1];
			godina = pom2[2];
			imePrezimeGodina = ime + " " + prezime + " " + godina;
			
			h = tabela.search(denMesec);
			if (h == -1) {
				tabela.insert(denMesec, imePrezimeGodina);
			} else {
				imePrezimeNovo = tabela.buckets[h].value;
				imePrezimeNovo += ";" + imePrezimeGodina;
				tabela.insert(denMesec, imePrezimeNovo);
			}
		}
		
		// System.out.println(tabela);

		s = br.readLine();
		pom3 = s.split("/");
		denMesecBaraj = pom3[0] + pom3[1];
		godinaBaraj = pom3[2];
		
		h = tabela.search(denMesecBaraj);
		if (h != -1) {
			pom4 = tabela.buckets[h].value.split(";");
			Arrays.sort(pom4);
			for (i = 0; i < pom4.length; i ++) {
				pom5 = pom4[i].split(" ");
				imePrezime = pom5[0] + " " + pom5[1];
				godina = pom5[2];
				godinaRezultat = Integer.parseInt(godinaBaraj) - Integer.parseInt(godina);
				rezultat = imePrezime + " " + godinaRezultat;
				System.out.println(rezultat);
			}
		} else {
			System.out.println("Nema");
		}

	}
	
	/*
???????????? ?? ?????????? ???? ???????? ???? ???????????? ???? ???????? ?????????????????? ???? ???????? ????????????????????????. 
???????? ???????????? ?? ???? ?????????? ?????????? ???? ???????????????????? ?????? ?????????????????? ???? ???????????????????????????? ???? ???????????? ???????????????? ?????? ?????? 
?? ?????????? ???????????? ???? ???????????? (?????????????????? ???????????? ?????????? ???? ??????????????????????). 
?????????????? ???? ???????????????? ?????????? ???????? ?????????????????? ???? ???? ???????????????? Nema.

3
Magdalena Kostoska 15/05/1982
Hristina Mihajloska 30/05/1984
Ilinka Ivanoska 15/05/1986
15/05/2016

Ilinka Ivanoska 30
Magdalena Kostoska 34
	 */

}
