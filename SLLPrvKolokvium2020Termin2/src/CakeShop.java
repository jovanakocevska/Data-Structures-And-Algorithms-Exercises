import java.util.Scanner;

class SLLNode {
	String name;
	int price;
	SLLNode succ;

	public SLLNode(String name, int price, SLLNode succ) {
		this.name = name;
		this.price = price;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return name;
	}
}

class SLL {
	SLLNode first;

	public SLL() {
		this.first = null;
	}

	public void insertFirst(String name, int price) {
		SLLNode ins = new SLLNode(name, price, first);
		first = ins;
	}

	public void insertLast(String name, int price) {
		if (first != null) {
			SLLNode tmp = first;
			while (tmp.succ != null)
				tmp = tmp.succ;
			SLLNode ins = new SLLNode(name, price, null);
			tmp.succ = ins;
		} else {
			insertFirst(name, price);
		}
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (first != null) {
			SLLNode tmp = first;
			ret.append(tmp).append("\n");
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret.append(tmp).append("\n");
			}
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
}

public class CakeShop {

	public static void removeCakes(SLL cakes) {

		SLLNode p = cakes.first;
		float prosek = 0;
		int brojac = 0;

		while (p != null) {
			prosek += p.price;
			brojac++;
			p = p.succ;
		}

		prosek = prosek / brojac;

		p = cakes.first;
		while (p != null) {
			if (p.price > prosek) {
				cakes.delete(p);
			}
			p = p.succ;
		}

	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		SLL cakes = new SLL();

		for (int i = 0; i < n; i++) {
			String line = scanner.nextLine();
			String[] parts = line.split("\\s+");
			cakes.insertLast(parts[0], Integer.parseInt(parts[1]));
		}

		removeCakes(cakes);
		System.out.println(cakes.toString());
	}
}