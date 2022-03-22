import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DLLNode<E> {
	protected E element;
	protected DLLNode<E> pred, succ;

	public DLLNode(E element, DLLNode<E> pred, DLLNode<E> succ) {
		this.element = element;
		this.pred = pred;
		this.succ = succ;
	}

	public String toString() {
		return element.toString();
	}
}

class DLL<E> {
	private DLLNode<E> first;
	private DLLNode<E> last;

	public DLL() {
		first = null;
		last = null;
	}

	public DLLNode<E> getFirst() {
		return first;
	}

	public DLLNode<E> getLast() {
		return last;
	}

	public void deleteList() {
		first = null;
		last = null;
	}

	public int length() {
		int brojac = 0;
		if (first != null) {
			DLLNode<E> tmp = first;
			while (tmp != null) {
				brojac++;
				tmp = tmp.succ;
			}
		}
		return brojac;
	}

	public void insertFirst(E o) {
		DLLNode<E> nov = new DLLNode<E>(o, null, first);
		if (first == null)
			first = last = nov;
		else {
			first.pred = nov;
			first = nov;
		}
	}

	public void insertLast(E o) {
		DLLNode<E> nov = new DLLNode<E>(o, last, null);
		if (first == null) {
			first = last = nov;
		} else {
			last.succ = nov;
			last = nov;
		}
	}

	public void insertAfter(E o, DLLNode<E> after) {
		if (after == last) {
			insertLast(o);
			return;
		}
		DLLNode<E> nov = new DLLNode<E>(o, after, after.succ);
		after.succ.pred = nov;
		after.succ = nov;
	}

	public void insertBefore(E o, DLLNode<E> before) {
		if (before == first) {
			insertFirst(o);
			return;
		}

		DLLNode<E> nov = new DLLNode<E>(o, before.pred, before);
		before.pred.succ = nov;
		before.pred = nov;
	}

	public E deleteFirst() {
		if (first == null)
			return null;
		DLLNode<E> tmp = first;
		first = first.succ;
		if (first == null) {
			last = null;
		} else
			first.pred = null;
		return tmp.element;
	}

	public E deleteLast() {
		if (first == null)
			return null;
		DLLNode<E> tmp = last;
		last = last.pred;
		if (last == null) {
			first = null;
		} else
			last.succ = null;
		return tmp.element;
	}

	public E delete(DLLNode<E> node) {
		if (first == node) {
			deleteFirst();
			return node.element;
		}
		if (last == node) {
			deleteLast();
			return node.element;
		}
		node.succ.pred = node.pred;
		node.pred.succ = node.succ;
		return node.element;
	}

	public DLLNode<E> find(E o) {
		if (first != null) {
			DLLNode<E> tmp = first;
			while (tmp != null && tmp.element != o) {
				tmp = tmp.succ;
			}
			if (tmp == null) {
				System.out.println("Elementot ne postoi vo listata!");
				return null;
			} else
				return tmp;
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

	public DLLNode<E> findLast(E o) {
		if (first != null) {
			DLLNode<E> tmp = last;
			while (tmp != null && tmp.element != o) {
				tmp = tmp.pred;
			}
			if (tmp == null) {
				System.out.println("Elementot ne postoi vo listata!");
				return null;
			} else
				return tmp;
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

	public String toString() {
		String ret = new String();
		if (first != null) {
			DLLNode<E> tmp = first;
			while (tmp != null) {
				ret += tmp + "<->";
				tmp = tmp.succ;
			}
		} else
			ret = "Prazna lista";
		return ret;
	}

	public String toStringR() {
		String ret = new String();
		if (first != null) {
			DLLNode<E> tmp = last;
			while (tmp != null) {
				ret += tmp + "<->";
				tmp = tmp.pred;
			}
		} else
			ret = "Listata e prazna";
		return ret;
	}
}

public class DLLDvojnoPovrzanaLista {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int N, i, broj; // float broj; //double broj;
		String[] pom;

		DLL<Integer> lista1 = new DLL<Integer>(); // DLL<Float> lista 1 = new DLL<Float>();
		DLL<Integer> lista2 = new DLL<Integer>(); // DLL<Double> lista 1 = new DLL<Double>();
		DLL<Integer> lista3 = new DLL<Integer>();

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			broj = Integer.parseInt(pom[i]);
			lista1.insertLast(broj);
		}

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			broj = Integer.parseInt(pom[i]); // broj = Float.parseFloat(pom[i]); //broj = Double.parseDouble(pom[i]);
			lista2.insertLast(broj);
		}

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			broj = Integer.parseInt(pom[i]);
			lista3.insertLast(broj);
		}

		// vashata funkcija tuka

		System.out.println(lista1);
		System.out.println(lista2);
		System.out.println(lista3);

		char c;
		DLL<Character> lista4 = new DLL<Character>();
		DLL<Character> lista5 = new DLL<Character>();
		DLL<Character> lista6 = new DLL<Character>();

		s = br.readLine();
		for (i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			lista4.insertLast(c);
		}

		s = br.readLine();
		for (i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			lista5.insertLast(c);
		}

		s = br.readLine();
		for (i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			lista6.insertLast(c);
		}

		// vashata funkcija tuka

		System.out.println(lista4);
		System.out.println(lista5);
		System.out.println(lista6);

		DLL<String> lista7 = new DLL<String>();
		DLL<String> lista8 = new DLL<String>();
		DLL<String> lista9 = new DLL<String>();

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			lista7.insertLast(pom[i]);
		}

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			lista8.insertLast(pom[i]);
		}

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			lista9.insertLast(pom[i]);
		}

		// vashata funkcija tuka

		System.out.println(lista7);
		System.out.println(lista8);
		System.out.println(lista9);
	}

}

/*
10
1 2 3 4 5 6 7 8 9 10
5
11 22 33 44 55
7
23 25 67 12 67 23 56
katerina
milica
ana
4
Kate e dobra uchitelka
3
avda2342 av4235 jktr34bks
4
Vie ste super studenti
*/

