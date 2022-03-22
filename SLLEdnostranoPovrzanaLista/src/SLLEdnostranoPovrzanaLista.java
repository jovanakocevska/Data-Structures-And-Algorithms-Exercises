import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class SLLNode<E> {
	protected E element;
	protected SLLNode<E> succ;

	public SLLNode(E element, SLLNode<E> succ) {
		this.element = element;
		this.succ = succ;
	}

	public String toString() {
		return element.toString();
	}
}

class SLL<E> {
	private SLLNode<E> first;

	public SLL() {
		first = null;
	}

	public SLLNode<E> getFirst() {
		return first;
	}

	public void deleteList() {
		first = null;
	}

	public int length() {
		int brojac = 0;
		if (first != null) {
			SLLNode<E> tmp = first;
			while (tmp != null) {
				brojac++;
				tmp = tmp.succ;
			}
		}
		return brojac;
	}

	public String toString() {
		String ret = new String();
		if (first != null) {
			SLLNode<E> tmp = first;
			while (tmp != null) {
				ret += tmp.toString() + "->";
				tmp = tmp.succ;
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}

	public void instertAfter(E o, SLLNode<E> node) {
		if (node == null)
			System.out.println("Dadeniot jazol e null");
		else {
			SLLNode<E> nov = new SLLNode<E>(o, node.succ);
			node.succ = nov;
		}
	}

	public void insertBefore(E o, SLLNode<E> before) {
		if (first != null) {
			SLLNode<E> tmp = first;
			if (first == before) {
				SLLNode<E> nov = new SLLNode<E>(o, first);
				first = nov;
				return;
			}
			while (tmp.succ != null && tmp.succ != before) {
				tmp = tmp.succ;
			}
			if (tmp.succ == before) {
				SLLNode<E> nov = new SLLNode<E>(o, before);
				tmp.succ = nov;
			} else
				System.out.println("Elementot ne postoi vo listata");
		} else
			System.out.println("Listata e prazna");
	}

	public void insertLast(E o) {
		if (first == null) {
			SLLNode<E> nov = new SLLNode<E>(o, null);
			first = nov;
		} else {
			SLLNode<E> tmp = first;
			while (tmp.succ != null)
				tmp = tmp.succ;
			SLLNode<E> nov = new SLLNode<E>(o, null);
			tmp.succ = nov;
		}
	}

	public E deleteFirst() {
		if (first == null) {
			System.out.println("Listata e prazna");
			return null;
		} else {
			SLLNode<E> tmp = first;
			first = first.succ;
			return tmp.element;
		}
	}

	public E delete(SLLNode<E> node) {
		if (first != null) {
			SLLNode<E> tmp = first;
			if (node == first) {
				first = first.succ;
				return node.element;
			}
			while (tmp.succ != null && tmp.succ != node) {
				tmp = tmp.succ;
			}
			if (tmp.succ == null) {
				System.out.println("Elementot ne postoi");
				return null;
			} else {
				tmp.succ = node.succ;
				return node.element;
			}
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

	public SLLNode<E> find(E o) {
		if (first != null) {
			SLLNode<E> tmp = first;
			while (tmp != null && tmp.element != o) {
				tmp = tmp.succ;
			}
			if (tmp == null) {
				System.out.println("Elementot ne postoi vo listata");
				return null;
			} else
				return tmp;
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

	public SLLNode<E> findLast(E o) {
		SLLNode<E> find = null;
		if (first != null) {
			SLLNode<E> tmp = first;
			while (tmp != null) {
				if (tmp.element == o)
					find = tmp;
				tmp = tmp.succ;
			}
			if (find == null) {
				System.out.println("Elementot ne postoi vo listata");
				return null;
			} else
				return find;
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

}

public class SLLEdnostranoPovrzanaLista {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int N, i, broj; // float broj; //double broj;
		String[] pom;

		SLL<Integer> lista1 = new SLL<Integer>(); // SLL<Float> lista 1 = new SLL<Float>();
		SLL<Integer> lista2 = new SLL<Integer>(); // SLL<Double> lista 1 = new SLL<Double>();
		SLL<Integer> lista3 = new SLL<Integer>();

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
		SLL<Character> lista4 = new SLL<Character>();
		SLL<Character> lista5 = new SLL<Character>();
		SLL<Character> lista6 = new SLL<Character>();

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

		SLL<String> lista7 = new SLL<String>();
		SLL<String> lista8 = new SLL<String>();
		SLL<String> lista9 = new SLL<String>();

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
avda2342 av423f jktr34bks
4
Vie ste super studenti
*/

