import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

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

interface Queue<E> {

	// Elementi na redicata se objekti od proizvolen tip.

	// Metodi za pristap:

	public boolean isEmpty();
	// Vrakja true ako i samo ako redicata e prazena.

	public int size();
	// Ja vrakja dolzinata na redicata.

	public E peek();
	// Go vrakja elementot na vrvot t.e. pocetokot od redicata.

	// Metodi za transformacija:

	public void clear();
	// Ja prazni redicata.

	public void enqueue(E x);
	// Go dodava x na kraj od redicata.

	public E dequeue();
	// Go otstranuva i vrakja pochetniot element na redicata.

}

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

	public boolean isFull() {
		return elems.length == depth;
	}

}

class ArrayQueue<E> implements Queue<E> {

	// Redicata e pretstavena na sledniot nacin:
	// length go sodrzi brojot na elementi.
	// Ako length > 0, togash elementite na redicata se zachuvani vo
	// elems[front...rear-1]
	// Ako rear > front, togash vo elems[front...maxlength-1] i elems[0...rear-1]
	E[] elems;
	int length, front, rear;

	// Konstruktor ...

	@SuppressWarnings("unchecked")
	public ArrayQueue(int maxlength) {
		elems = (E[]) new Object[maxlength];
		clear();
	}

	public boolean isEmpty() {
		// Vrakja true ako i samo ako redicata e prazena.
		return (length == 0);
	}

	public int size() {
		// Ja vrakja dolzinata na redicata.
		return length;
	}

	public E peek() {
		// Go vrakja elementot na vrvot t.e. pocetokot od redicata.
		if (length > 0)
			return elems[front];
		else
			throw new NoSuchElementException();
	}

	public void clear() {
		// Ja prazni redicata.
		length = 0;
		front = rear = 0; // arbitrary
	}

	public void enqueue(E x) {
		// Go dodava x na kraj od redicata.
		elems[rear++] = x;
		if (rear == elems.length)
			rear = 0;
		length++;
	}

	public E dequeue() {
		// Go otstranuva i vrakja pochetniot element na redicata.
		if (length > 0) {
			E frontmost = elems[front];
			elems[front++] = null;
			if (front == elems.length)
				front = 0;
			length--;
			return frontmost;
		} else
			throw new NoSuchElementException();
	}
}

class StackQueue {
	ArrayStack<Integer> as;
	ArrayQueue<Integer> aq;

	public StackQueue(int sizeStack, int sizeQueue) {
		as = new ArrayStack<Integer>(sizeStack);
		aq = new ArrayQueue<Integer>(sizeQueue);
	}

	public void InsertElement(int x) {
		if (!as.isFull()) {
			as.push(x);
		} else {
			aq.enqueue(x);
		}
	}

	public int DeleteElement() {
		if (as.isEmpty() && aq.isEmpty()) {
			throw new NoSuchElementException();
		}
		if (as.isEmpty()) {
			return aq.dequeue();
		}
		if (aq.isEmpty()) {
			return as.pop();
		}
		if (as.peek() > aq.peek()) {
			return as.pop();
		}
		if (aq.peek() > as.peek()) {
			return aq.dequeue();
		} else
			return aq.dequeue();
	}

	public boolean isEmpty() {
		if (as.isEmpty() && aq.isEmpty()) {
			return true;
		} else
			return false;
	}

	public int Peek() {
		if (as.isEmpty() && aq.isEmpty()) {
			throw new NoSuchElementException();
		}
		if (as.isEmpty()) {
			return aq.peek();
		}
		if (aq.isEmpty()) {
			return as.peek();
		}
		if (as.peek() > aq.peek()) {
			return as.peek();
		} else
			return aq.peek();
	}

}

/*
3
5
8
0
2
4
1
3
5
7
9

4
4 2 1 3 5 7 9 0
 */

public class SQStackQueue {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int i, N, sizeStack, sizeQueue, broj;
		String s;

		s = br.readLine();
		sizeStack = Integer.parseInt(s);

		s = br.readLine();
		sizeQueue = Integer.parseInt(s);

		StackQueue sq = new StackQueue(sizeStack, sizeQueue);

		s = br.readLine();
		N = Integer.parseInt(s);

		for (i = 0; i < N; i++) {
			s = br.readLine();
			broj = Integer.parseInt(s);
			sq.InsertElement(broj);
		}

		System.out.println(sq.Peek());

		// String ret = " ";

		while (!sq.isEmpty()) {
			broj = sq.DeleteElement();
			System.out.print(broj + " "); // ret += broj + " ";
		}

		// Systen.out.println(ret.trim());
		// trim() funkcija koja krati prazni mesta levo i desno
		// trimLeft() - krati prazni mesta levo, trimRight() - krati prazni mesta desno

	}

}
