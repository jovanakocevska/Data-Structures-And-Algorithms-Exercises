import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

class LinkedStack<E> implements Stack<E> {

	// Stekot e pretstaven na sledniot nacin: top e link do prviot jazol
	// na ednostrano-povrzanata lista koja sodrzi gi elementite na stekot .
	private SLLNode<E> top;

	public LinkedStack() {
		// Konstrukcija na nov, prazen stek.
		top = null;
	}

	public boolean isEmpty() {
		// Vrakja true ako i samo ako stekot e prazen.
		return (top == null);
	}

	public E peek() {
		// Go vrakja elementot na vrvot od stekot.
		if (top == null)
			throw new NoSuchElementException();
		return top.element;
	}

	public void clear() {
		// Go prazni stekot.
		top = null;
	}

	public void push(E x) {
		// Go dodava x na vrvot na stekot.
		top = new SLLNode<E>(x, top);
	}

	public E pop() {
		// Go otstranuva i vrakja elementot shto e na vrvot na stekot.
		if (top == null)
			throw new NoSuchElementException();
		E topElem = top.element;
		top = top.succ;
		return topElem;
	}

}

interface Stack<E> {

	// Elementi na stekot se objekti od proizvolen tip.

	// Metodi za pristap:

	public boolean isEmpty();
	// Vrakja true ako i samo ako stekot e prazen.

	public E peek();
	// Go vrakja elementot na vrvot od stekot.

	// Metodi za transformacija:

	public void clear();
	// Go prazni stekot.

	public void push(E x);
	// Go dodava x na vrvot na stekot.

	public E pop();
	// Go otstranuva i vrakja elementot shto e na vrvot na stekot.
}

public class StackPostfixNotacija {

	public static int PostfixNotacija(String s) {

		int broj, broj1, broj2, i;
		String[] pom = s.split(" ");

		LinkedStack<Integer> stack = new LinkedStack<Integer>();

		for (i = 0; i < pom.length; i++) {
			if (pom[i].equals("+") || pom[i].equals("-") || pom[i].equals("*") || pom[i].equals("/")) {
				broj2 = stack.pop();
				broj1 = stack.pop();
				if (pom[i].equals("+")) {
					broj = broj1 + broj2;
					stack.push(broj);
				}
				if (pom[i].equals("-")) {
					broj = broj1 - broj2;
					stack.push(broj);
				}
				if (pom[i].equals("*")) {
					broj = broj1 * broj2;
					stack.push(broj);
				}
				if (pom[i].equals("/")) {
					broj = broj1 / broj2;
					stack.push(broj);
				}
			} else {
				broj = Integer.parseInt(pom[i]);
				stack.push(broj);
			}
		}

		int rezultat = stack.pop();

		return rezultat;

	}
	
	public static int PostfixNotacija2(String s) {

		int broj, broj1, broj2, i;
		String br = "";

		LinkedStack<Integer> stack = new LinkedStack<Integer>();

		for (i = 0; i < s.length(); i++) {
			br = "";
			while (i<s.length() && Character.isDigit(s.charAt(i))) {
				br+=s.charAt(i);
				i++;
			}
			if(!br.equals("")) {
				broj = Integer.parseInt(br);
				stack.push(broj);
			}
			if(s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/') {
				broj2 = stack.pop();
				broj1 = stack.pop();
				if (s.charAt(i) == '+') {
					broj = broj1 + broj2;
					stack.push(broj);
				}
				if (s.charAt(i) == '-') {
					broj = broj1 - broj2;
					stack.push(broj);
				}
				if (s.charAt(i) == '*') {
					broj = broj1 * broj2;
					stack.push(broj);
				}
				if (s.charAt(i) == '/') {
					broj = broj1 / broj2;
					stack.push(broj);
				}
			}
		}
		
		int rezultat = stack.pop();

		return rezultat;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();

		int rezultat = PostfixNotacija(s);
		System.out.println(rezultat);
		
		System.out.println("\n");
		
		int rezultat2 = PostfixNotacija2(s);
		System.out.println(rezultat2);

	}

}
