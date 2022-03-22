import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

class SLLNode {
	String vozach;
	int gorivo;
	SLLNode succ;

	public SLLNode(String vozach, int gorivo, SLLNode succ) {
		this.vozach = vozach;
		this.gorivo = gorivo;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return vozach + "(" + gorivo + ")";
	}
}

class SLL {
	SLLNode first;

	public SLL() {
		this.first = null;
	}

	public void insertFirst(String vozach, int gorivo) {
		SLLNode ins = new SLLNode(vozach, gorivo, first);
		first = ins;
	}

	public void insertLast(String vozach, int gorivo) {
		if (first != null) {
			SLLNode tmp = first;
			while (tmp.succ != null)
				tmp = tmp.succ;
			SLLNode ins = new SLLNode(vozach, gorivo, null);
			tmp.succ = ins;
		} else {
			insertFirst(vozach, gorivo);
		}
	}

	public void insertAfter(String vozach, int gorivo, SLLNode node) {
		if (node != null) {
			SLLNode ins = new SLLNode(vozach, gorivo, node.succ);
			node.succ = ins;
		} else {
			System.out.println("Dadenot jazol e null");
		}
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (first != null) {
			SLLNode tmp = first;
			ret.append(tmp).append("->");
			while (tmp.succ.succ != null) {
				tmp = tmp.succ;
				ret.append(tmp).append("->");
			}
			ret.append(tmp);
		} else
			ret = new StringBuilder("NO ELEMENTS");
		return ret.toString();
	}

	public SLLNode deleteFirst() {
		if (first != null) {
			SLLNode tmp = first;
			first = first.succ;
			return tmp;
		} else {
			System.out.println("Listata e prazna");
			return null;
		}
	}

	public SLLNode delete(SLLNode node) {
		if (first != null) {
			SLLNode tmp = first;
			if (first == node) {
				return this.deleteFirst();
			}
			while (tmp.succ != node && tmp.succ.succ != null)
				tmp = tmp.succ;
			if (tmp.succ == node) {
				tmp.succ = tmp.succ.succ;
				return node;
			} else {
				System.out.println("Elementot ne postoi vo listata");
				return null;
			}
		} else {
			System.out.println("Listata e prazna");
			return null;
		}

	}

	public SLLNode find(String vozach) {
		if (first != null) {
			SLLNode tmp = first;
			while (!tmp.vozach.equals(vozach) && tmp.succ != null)
				tmp = tmp.succ;
			if (tmp.vozach.equals(vozach)) {
				return tmp;
			}
		}
		return null;
	}

}

public class SLLTrka {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		SLL formula = new SLL();
		SLLNode tmp;
		String vozach;
		int gorivo, j, novoGorivo;

		for (int i = 0; i < n; i++) {
			String line = scanner.nextLine();
			String[] parts = line.split("\\s+");
			formula.insertFirst(parts[0], Integer.parseInt(parts[1]));
		}

		System.out.println(formula.toString());

		n = Integer.parseInt(scanner.nextLine());

		for (int i = 0; i < n; i++) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
			vozach = parts[0];
			gorivo = Integer.parseInt(parts[1]);
			tmp = formula.find(vozach);
			if (tmp != null) {
				if ((tmp.gorivo - gorivo) < 0) {
					formula.delete(tmp);
				} else {
					j = 0;
					novoGorivo = tmp.gorivo - gorivo;
					formula.delete(tmp);
					while (tmp != null && j < gorivo) {
						tmp = tmp.succ;
						j++;
					}
					if (tmp == null) {
						formula.insertLast(vozach, novoGorivo);
					} else {
						formula.insertAfter(vozach, novoGorivo, tmp);
					}
				}
			}
			System.out.println(formula.toString());
		}
	}
}
