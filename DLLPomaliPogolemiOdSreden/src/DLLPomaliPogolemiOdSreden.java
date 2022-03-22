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

public class DLLPomaliPogolemiOdSreden {

	// eлементите во новата листа се сместуваат тргнувајќи од напред
	public static void pomaliPogolemiOdSreden_a(DLL<Integer> lista, DLL<Integer> pomali, DLL<Integer> pogolemi) {

		DLLNode<Integer> tmp = lista.getFirst();
		DLLNode<Integer> pom = lista.getLast();
		int sreden = 0;

		while (tmp != null && pom != null) {
			if (tmp == pom) {
				sreden = tmp.element;
			}
			if (tmp.succ == pom) {
				sreden = tmp.element; // KOGA SE 2 SREDNI ELEMENTI, KOJ EDEN TREBA DA GO ZEME?
			}
			tmp = tmp.succ;
			pom = pom.pred;
		}
		
		/*
		while (p!=q && q.succ!=p) { 
			if (p.element != q.element) {
				return -1;
			}
			p = p.succ;
			q = q.pred;
		}
		*/

		tmp = lista.getFirst();
		while (tmp != null) {
			if (tmp.element < sreden) {
				pomali.insertLast(tmp.element);
			}
			if (tmp.element > sreden) {
				pogolemi.insertLast(tmp.element);
			}
			tmp = tmp.succ;
		}
	}

	// eлементите во новата листа се сместуваат тргнувајќи од назад
	public static void pomaliPogolemiOdSreden_b(DLL<Integer> lista, DLL<Integer> pomali, DLL<Integer> pogolemi) {

		DLLNode<Integer> tmp = lista.getFirst();
		DLLNode<Integer> pom = lista.getLast();
		int sreden = 0;

		while (tmp != null && pom != null) {
			if (tmp == pom) {
				sreden = tmp.element;
			}
			if (tmp.succ == pom) {
				sreden = tmp.element;
			}
			tmp = tmp.succ;
			pom = pom.pred;
		}

		tmp = lista.getLast();
		while (tmp != null) {
			if (tmp.element < sreden) {
				pomali.insertLast(tmp.element);
			}
			if (tmp.element > sreden) {
				pogolemi.insertLast(tmp.element);
			}
			tmp = tmp.pred;
		}

	}

	// помали од напред
	// поголеми од назад
	public static void pomaliPogolemiOdSreden_c(DLL<Integer> lista, DLL<Integer> pomali, DLL<Integer> pogolemi) {

		DLLNode<Integer> tmp = lista.getFirst();
		DLLNode<Integer> pom = lista.getLast();
		int sreden = 0;

		while (tmp != null && pom != null) {
			if (tmp == pom) {
				sreden = tmp.element;
			}
			if (tmp.succ == pom) {
				sreden = tmp.element;
			}
			tmp = tmp.succ;
			pom = pom.pred;
		}

		DLLNode<Integer> tmp_pomali = lista.getFirst();
		while (tmp_pomali != null) {
			if (tmp_pomali.element < sreden) {
				pomali.insertLast(tmp_pomali.element);
			}
			tmp_pomali = tmp_pomali.succ;
		}

		DLLNode<Integer> tmp_pogolemi = lista.getLast();
		while (tmp_pogolemi != null) {
			if (tmp_pogolemi.element > sreden) {
				pogolemi.insertLast(tmp_pogolemi.element);
			}
			tmp_pogolemi = tmp_pogolemi.pred;
		}
	}

	// помали од назад
	// поголеми од напред
	public static void pomaliPogolemiOdSreden_d(DLL<Integer> lista, DLL<Integer> pomali, DLL<Integer> pogolemi) {

		DLLNode<Integer> tmp = lista.getFirst();
		DLLNode<Integer> pom = lista.getLast();
		int sreden = 0;

		while (tmp != null && pom != null) {
			if (tmp == pom) {
				sreden = tmp.element;
			}
			if (tmp.succ == pom) {
				sreden = tmp.element;
			}
			tmp = tmp.succ;
			pom = pom.pred;
		}

		DLLNode<Integer> tmp_pomali = lista.getLast();
		while (tmp_pomali != null) {
			if (tmp_pomali.element < sreden) {
				pomali.insertLast(tmp_pomali.element);
			}
			tmp_pomali = tmp_pomali.pred;
		}

		DLLNode<Integer> tmp_pogolemi = lista.getFirst();
		while (tmp_pogolemi != null) {
			if (tmp_pogolemi.element > sreden) {
				pogolemi.insertLast(tmp_pogolemi.element);
			}
			tmp_pogolemi = tmp_pogolemi.succ;
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		int N, i, broj;
		String[] pom;

		DLL<Integer> lista = new DLL<Integer>();
		DLL<Integer> pomali = new DLL<Integer>();
		DLL<Integer> pogolemi = new DLL<Integer>();

		s = br.readLine();
		N = Integer.parseInt(s);
		s = br.readLine();
		pom = s.split(" ");
		for (i = 0; i < N; i++) {
			broj = Integer.parseInt(pom[i]);
			lista.insertLast(broj);
		}

		System.out.println('\n');

		pomaliPogolemiOdSreden_a(lista, pomali, pogolemi);
		System.out.println(pomali);
		System.out.println(pogolemi);
		System.out.println('\n');

		pomali = new DLL<Integer>();
		pogolemi = new DLL<Integer>();
		
		pomaliPogolemiOdSreden_b(lista, pomali, pogolemi);
		System.out.println(pomali);
		System.out.println(pogolemi);
		System.out.println('\n');
		
		pomali = new DLL<Integer>();
		pogolemi = new DLL<Integer>();

		pomaliPogolemiOdSreden_c(lista, pomali, pogolemi);
		System.out.println(pomali);
		System.out.println(pogolemi);
		System.out.println('\n');
		
		pomali = new DLL<Integer>();
		pogolemi = new DLL<Integer>();

		pomaliPogolemiOdSreden_d(lista, pomali, pogolemi);
		System.out.println(pomali);
		System.out.println(pogolemi);

	}

}
