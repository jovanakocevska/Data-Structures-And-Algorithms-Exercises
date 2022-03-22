import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
				ret += tmp.toString() + " ";
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

public class SLLSpojListiNaizmenichno {

	public static void SpojListi(SLL<Integer> lista1, SLL<Integer> lista2, SLL<Integer> rezultat) {
		SLLNode<Integer> p = lista1.getFirst();
		SLLNode<Integer> q = lista2.getFirst();

		while (p != null && q != null) {
			rezultat.insertLast(p.element);
			p = p.succ;
			if (p != null) {
				rezultat.insertLast(p.element);
				p = p.succ;
			}

			rezultat.insertLast(q.element);
			q = q.succ;
			if (q != null) {
				rezultat.insertLast(q.element);
				q = q.succ;
			}
		}

		while (p != null) {
			rezultat.insertLast(p.element);
			p = p.succ;
		}

		while (q != null) {
			rezultat.insertLast(q.element);
			q = q.succ;
		}
	}

	public static void SpojListi2(SLL<Integer> lista1, SLL<Integer> lista2, SLL<Integer> rezultat) {
		SLLNode<Integer> p = lista1.getFirst();
		SLLNode<Integer> q = lista2.getFirst();

		while (p != null || q != null) {
			rezultat.insertLast(p.element);
			p = p.succ;
			if (p != null) {
				rezultat.insertLast(p.element);
				p = p.succ;
			}
			if (p != null) {
				rezultat.insertLast(p.element);
				p = p.succ;
			}
			if (q != null) {
				rezultat.insertLast(q.element);
				q = q.succ;
			}
			if (q != null) {
				rezultat.insertLast(q.element);
				q = q.succ;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int N, i, broj;
		String[] pom;

		SLL<Integer> lista1 = new SLL<Integer>();
		SLL<Integer> lista2 = new SLL<Integer>();
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
			broj = Integer.parseInt(pom[i]);
			lista2.insertLast(broj);
		}

		SpojListi(lista1, lista2, lista3);
		System.out.println(lista3);
	}

}
