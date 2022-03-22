import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

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

class Covek {

	String imePrezime;
	int licnaKarta;
	int pasos;
	int vozackaDozvola;

	public Covek(String imePrezime, int licnaKarta, int pasos, int vozackaDozvola) {
		super();
		this.imePrezime = imePrezime;
		this.licnaKarta = licnaKarta;
		this.pasos = pasos;
		this.vozackaDozvola = vozackaDozvola;
	}

}

public class QueueMVR {

	public static void MVR(ArrayQueue<Covek> licnaKarta, ArrayQueue<Covek> pasos, ArrayQueue<Covek> vozackaDozvola) {

		Covek c;

		while (!licnaKarta.isEmpty()) {
			c = licnaKarta.dequeue();
			c.licnaKarta = 0;
			if (c.pasos == 1) {
				pasos.enqueue(c);
			} else if (c.vozackaDozvola == 1) {
				vozackaDozvola.enqueue(c);
			} else {
				System.out.println(c.imePrezime);
			}
		}

		while (!pasos.isEmpty()) {
			c = pasos.dequeue();
			c.pasos = 0;
			if (c.vozackaDozvola == 1) {
				vozackaDozvola.enqueue(c);
			} else {
				System.out.println(c.imePrezime);
			}
		}

		while (!vozackaDozvola.isEmpty()) {
			c = vozackaDozvola.dequeue();
			c.vozackaDozvola = 0;
			System.out.println(c.imePrezime);
		}

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n, i, licnaKarta, pasos, vozackaDozvola;
		String s, imePrezime;
		Covek c;

		s = br.readLine();
		n = Integer.parseInt(s);

		ArrayQueue<Covek> LicniKarti = new ArrayQueue<Covek>(n);
		ArrayQueue<Covek> Pasosi = new ArrayQueue<Covek>(n);
		ArrayQueue<Covek> VozackiDozvoli = new ArrayQueue<Covek>(n);

		for (i = 0; i < n; i++) {
			imePrezime = br.readLine();

			s = br.readLine();
			licnaKarta = Integer.parseInt(s);

			s = br.readLine();
			pasos = Integer.parseInt(s);

			s = br.readLine();
			vozackaDozvola = Integer.parseInt(s);

			c = new Covek(imePrezime, licnaKarta, pasos, vozackaDozvola);

			if (licnaKarta == 1) {
				LicniKarti.enqueue(c);
			} else if (pasos == 1) {
				Pasosi.enqueue(c);
			} else if (vozackaDozvola == 1) {
				VozackiDozvoli.enqueue(c);
			}
		}

		MVR(LicniKarti, Pasosi, VozackiDozvoli);

	}
}
