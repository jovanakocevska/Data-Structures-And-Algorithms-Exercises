import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

class ArrayStack<E> implements Stack<E> {

	// Stekot e pretstaven na sledniot nacin:
	// depth e dlabochinata na stekot, a
	// elems[0...depth-1] se negovite elementi.
	private E[] elems;
	private int depth;

	@SuppressWarnings("unchecked")
	public ArrayStack(int maxDepth) {
		// Konstrukcija na nov, prazen stek.
		elems = (E[]) new Object[maxDepth];
		depth = 0;
	}

	public boolean isEmpty() {
		// Vrakja true ako i samo ako stekot e prazen.
		return (depth == 0);
	}

	public E peek() {
		// Go vrakja elementot na vrvot od stekot.
		if (depth == 0)
			throw new NoSuchElementException();
		return elems[depth - 1];
	}

	public void clear() {
		// Go prazni stekot.
		for (int i = 0; i < depth; i++)
			elems[i] = null;
		depth = 0;
	}

	public void push(E x) {
		// Go dodava x na vrvot na stekot.
		elems[depth++] = x;
	}

	public E pop() {
		// Go otstranuva i vrakja elementot shto e na vrvot na stekot.
		if (depth == 0)
			throw new NoSuchElementException();
		E topmost = elems[--depth];
		elems[depth] = null;
		return topmost;
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

public class StackKorektenIzraz {

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
		ArrayStack<Character> stack = new ArrayStack<Character>(s.length());

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
