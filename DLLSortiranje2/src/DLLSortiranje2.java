import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

class DLLNode<E> {
	protected E element;
	protected DLLNode<E> pred, succ;

	public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
		this.element = elem;
		this.pred = pred;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}

class DLL<E> {
	private DLLNode<E> first, last;

	public DLL() {
		// Construct an empty SLL
		this.first = null;
		this.last = null;
	}

	public void deleteList() {
		first = null;
		last = null;
	}

	public int length() {
		int ret;
		if (first != null) {
			DLLNode<E> tmp = first;
			ret = 1;
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret++;
			}
			return ret;
		} else
			return 0;

	}

	public DLLNode<E> find(E o) {
		if (first != null) {
			DLLNode<E> tmp = first;
			while (tmp.element != o && tmp.succ != null)
				tmp = tmp.succ;
			if (tmp.element == o) {
				return tmp;
			} else {
				System.out.println("Elementot ne postoi vo listata");
			}
		} else {
			System.out.println("Listata e prazna");
		}
		return first;
	}

	public void insertFirst(E o) {
		DLLNode<E> ins = new DLLNode<E>(o, null, first);
		if (first == null)
			last = ins;
		else
			first.pred = ins;
		first = ins;
	}

	public void insertLast(E o) {
		if (first == null)
			insertFirst(o);
		else {
			DLLNode<E> ins = new DLLNode<E>(o, last, null);
			last.succ = ins;
			last = ins;
		}
	}

	public void insertAfter(E o, DLLNode<E> after) {
		if (after == last) {
			insertLast(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
		after.succ.pred = ins;
		after.succ = ins;
	}

	public void insertBefore(E o, DLLNode<E> before) {
		if (before == first) {
			insertFirst(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
		before.pred.succ = ins;
		before.pred = ins;
	}

	public E deleteFirst() {
		if (first != null) {
			DLLNode<E> tmp = first;
			first = first.succ;
			if (first != null)
				first.pred = null;
			if (first == null)
				last = null;
			return tmp.element;
		} else
			return null;
	}

	public E deleteLast() {
		if (first != null) {
			if (first.succ == null)
				return deleteFirst();
			else {
				DLLNode<E> tmp = last;
				last = last.pred;
				last.succ = null;
				return tmp.element;
			}
		}
		// else throw Exception
		return null;
	}

	public E delete(DLLNode<E> node) {
		if (node == first) {
			deleteFirst();
			return node.element;
		}
		if (node == last) {
			deleteLast();
			return node.element;
		}
		node.pred.succ = node.succ;
		node.succ.pred = node.pred;
		return node.element;

	}

	@Override
	public String toString() {
		String ret = new String();
		if (first != null) {
			DLLNode<E> tmp = first;
			ret += tmp + " ";
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret += tmp + " ";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}

	public String toStringR() {
		String ret = new String();
		if (last != null) {
			DLLNode<E> tmp = last;
			ret += tmp + "<->";
			while (tmp.pred != null) {
				tmp = tmp.pred;
				ret += tmp + "<->";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}

	public DLLNode<E> getFirst() {
		return first;
	}

	public DLLNode<E> getLast() {

		return last;
	}

	public void izvadiDupliIPrebroj() {

	}
}

public class DLLSortiranje2 {

	public static void Sortiranje2(DLL<Integer> lista1, DLL<Integer> lista2) {
		
		DLLNode<Integer> p = lista1.getFirst();
		DLLNode<Integer> tmp = lista2.getFirst();
		float sredna = 0;
		
		while (tmp != null) {
			sredna = sredna + tmp.element;
			tmp = tmp.succ;
		}
		sredna = sredna / lista2.length();

		DLLNode<Integer> q = lista2.getFirst();
		while (q != null) {
			if (q.element < sredna) {
				p = lista1.getFirst();
				while (p != null && q.element > p.element) {
					p = p.succ;
				}
				if (p == null) {
					lista1.insertLast(q.element);
				} else {
					lista1.insertBefore(q.element, p);
				}
			}
			q = q.succ;
		}

	}

	// 44 5 16 33 2
	// 5

	// Дадена е двојно поврзана листа.
	// Да се напише функција која ќе создаде нова листа,
	// во која ќе ги префрли јазлите од првата листа со инфо поле помало или еднакво
	// од средната вредност на јазлите од првата листа.
	// Новата листа да биде постојано сортирана.

	// 10
	// 3 14 5 6 2 77 33 4 66 100

	// 2 3 4 5 6 14

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int N, i, broj;
		String[] pom;

		DLL<Integer> lista = new DLL<Integer>();
		DLL<Integer> nova = new DLL<Integer>();

		s = br.readLine();
		N = Integer.parseInt(s);

		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			broj = Integer.parseInt(pom[i]);
			lista.insertLast(broj);
		}

		Sortiranje2(nova, lista);

		System.out.println(nova);

	}

}
