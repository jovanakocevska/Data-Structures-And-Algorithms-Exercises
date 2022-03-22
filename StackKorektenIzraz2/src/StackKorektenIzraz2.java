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

public class StackKorektenIzraz2 {

	public static boolean KorektniZagradi(char levo, char desno) {
		if (levo == '(' && desno == ')') {
			return true;
		}
		if (levo == '[' && desno == ']') {
			return true;
		}
		if (levo == '{' && desno == '}') {
			return true;
		}
		return false;
	}

	public static boolean KorektenIzraz(String s) {
		char c, levo;
		LinkedStack<Character> stack = new LinkedStack<Character>();

		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (c == '(' || c == '[' || c == '{') {
				stack.push(c);
			} else if (c == ')' || c == ']' || c == '}') {
				if (stack.isEmpty())
					return false;
				levo = stack.pop();
				if (!KorektniZagradi(levo, c)) {
					return false;
				}
			}
		}
		if (stack.isEmpty()) {
			return true;
		} else
			return false; // return stack.isEmpty();
	}

	// {[(a+b)*(a-b)]+(a+d)} YES
	// {{(a+b)*(a-b)]+(a+d)} NO
	// {[(a+b)*(a-b)]+(a+d) NO
	// [(a+b)*(a-b)]+(a+d)} NO

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();

		boolean korekten = KorektenIzraz(s);
		if (korekten) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	}

}
