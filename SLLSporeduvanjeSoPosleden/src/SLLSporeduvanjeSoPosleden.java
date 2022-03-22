import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

class SLL<E> {
	protected SLLNode<E> first;

	public SLL() {
		// Construct an empty SLL
		this.first = null;
	}

	public void deleteList() {
		first = null;
	}

	public int length() {
		int ret;
		if (first != null) {
			SLLNode<E> tmp = first;
			ret = 1;
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret++;
			}
			return ret;
		} else
			return 0;

	}

	@Override
	public String toString() {
		String ret = new String();
		if (first != null) {
			SLLNode<E> tmp = first;
			ret += tmp + " ";
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret += tmp + " ";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}

	public void insertFirst(E o) {
		SLLNode<E> ins = new SLLNode<E>(o, first);
		first = ins;
	}

	public void insertAfter(E o, SLLNode<E> node) {
		if (node != null) {
			SLLNode<E> ins = new SLLNode<E>(o, node.succ);
			node.succ = ins;
		} else {
			System.out.println("Dadenot jazol e null");
		}
	}

	public void insertBefore(E o, SLLNode<E> before) {

		if (first != null) {
			SLLNode<E> tmp = first;
			if (first == before) {
				this.insertFirst(o);
				return;
			}
			// ako first!=before
			while (tmp.succ != before)
				tmp = tmp.succ;
			if (tmp.succ == before) {
				SLLNode<E> ins = new SLLNode<E>(o, before);
				tmp.succ = ins;
			} else {
				System.out.println("Elementot ne postoi vo listata");
			}
		} else {
			System.out.println("Listata e prazna");
		}
	}

	public void insertLast(E o) {
		if (first != null) {
			SLLNode<E> tmp = first;
			while (tmp.succ != null)
				tmp = tmp.succ;
			SLLNode<E> ins = new SLLNode<E>(o, null);
			tmp.succ = ins;
		} else {
			insertFirst(o);
		}
	}

	public E deleteFirst() {
		if (first != null) {
			SLLNode<E> tmp = first;
			first = first.succ;
			return tmp.element;
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

	public E delete(SLLNode<E> node) {
		if (first != null) {
			SLLNode<E> tmp = first;
			if (first == node) {
				return this.deleteFirst();
			}
			while (tmp.succ != node && tmp.succ.succ != null)
				tmp = tmp.succ;
			if (tmp.succ == node) {
				tmp.succ = tmp.succ.succ;
				return node.element;
			} else {
				System.out.println("Elementot ne postoi vo listata");
				return null;
			}
		} else {
			System.out.println("Listata e prazna");
			return null;
		}

	}

	public SLLNode<E> getFirst() {
		return first;
	}

	public SLLNode<E> find(E o) {
		if (first != null) {
			SLLNode<E> tmp = first;
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

	public Iterator<E> iterator() {
		// Return an iterator that visits all elements of this list, in left-to-right
		// order.
		return new LRIterator<E>();
	}

	// //////////Inner class ////////////

	private class LRIterator<E> implements Iterator<E> {

		private SLLNode<E> place, curr;

		private LRIterator() {
			place = (SLLNode<E>) first;
			curr = null;
		}

		public boolean hasNext() {
			return (place != null);
		}

		public E next() {
			if (place == null)
				throw new NoSuchElementException();
			E nextElem = place.element;
			curr = place;
			place = place.succ;
			return nextElem;
		}

		public void remove() {
			// Not implemented
		}
	}

	public void mirror() {
		if (first != null) {
			// m=nextsucc, p=tmp,q=next
			SLLNode<E> tmp = first;
			SLLNode<E> newsucc = null;
			SLLNode<E> next;

			while (tmp != null) {
				next = tmp.succ;
				tmp.succ = newsucc;
				newsucc = tmp;
				tmp = next;
			}
			first = newsucc;
		}

	}

	public void merge(SLL<E> in) {
		if (first != null) {
			SLLNode<E> tmp = first;
			while (tmp.succ != null)
				tmp = tmp.succ;
			tmp.succ = in.getFirst();
		} else {
			first = in.getFirst();
		}
	}
}

public class SLLSporeduvanjeSoPosleden {

	public static void sporeduvanjeSoPosleden(SLL<Integer> lista, SLL<Integer> pomali, SLL<Integer> pogolemi) {

		SLLNode<Integer> tmp = lista.getFirst();
		int posleden = 0;

		while (tmp != null) {
			if (tmp.succ == null) {
				posleden = tmp.element;
			}
			tmp = tmp.succ;
		}

		tmp = lista.getFirst();
		while (tmp != null) {
			if (tmp.element < posleden) { // se zema vo predvid posledniot, no ne se stava vo niedna lista
				pomali.insertLast(tmp.element);
			}
			if (tmp.element > posleden) {
				pogolemi.insertLast(tmp.element);
			}
			tmp = tmp.succ;
		}

	}

	public static void sporeduvanjeSoPosleden2(SLL<Integer> lista, SLL<Integer> pomali, SLL<Integer> pogolemi) {

		SLLNode<Integer> tmp = lista.getFirst();
		int posleden = 0;

		while (tmp != null) {
			if (tmp.succ == null) {
				posleden = tmp.element;
			}
			tmp = tmp.succ;
		}

		tmp = lista.getFirst();
		while (tmp != null) {
			if (tmp.element <= posleden) { // se zema vo previd i posledniot element i se stava vo niza pomali
				pomali.insertLast(tmp.element);
			}
			if (tmp.element > posleden) {
				pogolemi.insertLast(tmp.element);
			}
			tmp = tmp.succ;
		}

	}

	public static void sporeduvanjeSoPosleden3(SLL<Integer> lista, SLL<Integer> pomali, SLL<Integer> pogolemi) {

		SLLNode<Integer> tmp = lista.getFirst();
		int posleden = 0;

		while (tmp != null) {
			if (tmp.succ == null) {
				posleden = tmp.element;
			}
			tmp = tmp.succ;
		}

		tmp = lista.getFirst();
		while (tmp != null) {
			if (tmp.element < posleden) {
				pomali.insertLast(tmp.element);
			}
			if (tmp.element >= posleden) { // se zema vo previd i posledniot element i se stava vo niza pogolemi
				pogolemi.insertLast(tmp.element);
			}
			tmp = tmp.succ;
		}

	}

	public static void sporeduvanjeSoPosleden4(SLL<Integer> lista, SLL<Integer> pomali, SLL<Integer> pogolemi) {

		SLLNode<Integer> tmp = lista.getFirst();
		int posleden = 0;

		while (tmp != null) {
			if (tmp.succ == null) {
				posleden = tmp.element;
			}
			tmp = tmp.succ;
		}

		tmp = lista.getFirst();
		while (tmp.succ != null) {
			if (tmp.element < posleden) {
				pomali.insertLast(tmp.element);
			}
			if (tmp.element > posleden) { // ne se zema vo previd posledniot element i ne se stava vo niedna lista
				pogolemi.insertLast(tmp.element);
			}
			tmp = tmp.succ;
		}

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int N, i, broj;
		String[] pom;

		SLL<Integer> lista = new SLL<Integer>();
		SLL<Integer> pomali = new SLL<Integer>();
		SLL<Integer> pogolemi = new SLL<Integer>();

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			broj = Integer.parseInt(pom[i]);
			lista.insertLast(broj);
		}

		sporeduvanjeSoPosleden(lista, pomali, pogolemi); // se zema vo predvid posledniot, no ne se stava vo niedna
															// lista
		System.out.println(pomali);
		System.out.println(pogolemi);

		sporeduvanjeSoPosleden2(lista, pomali, pogolemi); // se zema vo previd i posledniot element i se stava vo lista
															// pomali
		System.out.println(pomali);
		System.out.println(pogolemi);

		sporeduvanjeSoPosleden3(lista, pomali, pogolemi); // se zema vo previd i posledniot element i se stava vo lista
															// pogolemi
		System.out.println(pomali);
		System.out.println(pogolemi);

		sporeduvanjeSoPosleden3(lista, pomali, pogolemi); // ne se zema vo previd posledniot element i ne se stava vo
															// niedna lista
		System.out.println(pomali);
		System.out.println(pogolemi);
	}

}
