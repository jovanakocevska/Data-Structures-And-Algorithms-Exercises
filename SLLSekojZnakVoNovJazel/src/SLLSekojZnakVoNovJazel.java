import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

class SLL<E> {
	private SLLNode<E> first;

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

public class SLLSekojZnakVoNovJazel {

	public static void SekojZnakVoNovJazel(SLL<String> lista, SLL<Character> nova) {

		SLLNode<String> tmp = lista.getFirst();
		char c;

		while (tmp != null) {
			String s = tmp.element;
			for (int i = 0; i < s.length(); i++) {
				c = s.charAt(i);
				nova.insertLast(c);
			}
			tmp = tmp.succ;
		}

	}

	public static void SekojZnakVoNovJazel2(SLL<String> lista, SLL<Character> nova) {

		SLLNode<String> p = lista.getFirst();
		String s;

		while (p != null) {
			s = p.element;
			for (int i = 0; i < s.length(); i++) {
				nova.insertLast(s.charAt(i));
			}
			p = p.succ;
		}

	}

	public static void SekojZnakVoNovJazel3(SLL<String> lista) {

		SLLNode<String> tmp = lista.getFirst();
		String k;
		char c;

		while (tmp != null) {
			String s = tmp.element;
			for (int i = 0; i < s.length(); i++) {
				k = "";
				k += s.charAt(i);
				// k = String.valueOf(s.charAt(i)) - vtor nacin
				lista.insertBefore(k, tmp);
			}
			lista.delete(tmp);
			tmp = tmp.succ;
		}

	}

	public static void SekojZnakVoNovJazel4(SLL<String> lista1) {
		String s;
		String[] m;
		SLLNode<String> p = lista1.getFirst();
		while (p != null) {
			s = p.element;
			for (int i = 0; i < s.length(); i++) {
				m = s.split("");
				for (i = 0; i < m.length; i++) {
					lista1.insertBefore(m[i], p);
				}

			}
			lista1.delete(p);
			p = p.succ;
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int i, N;
		String[] pom;

		SLL<String> lista = new SLL<String>();
		SLL<Character> nova = new SLL<Character>();

		s = br.readLine();
		N = Integer.parseInt(s);

		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			lista.insertLast(pom[i]);
		}

		// SekojZnakVoNovJazel(lista, nova);
		// System.out.println(nova);

		// nova = new SLL<Character>();
		// SekojZnakVoNovJazel2(lista, nova);
		// System.out.println(nova);

		// SekojZnakVoNovJazel3(lista);
		// System.out.println(lista);

		SekojZnakVoNovJazel4(lista);
		System.out.println(lista);

	}

}
