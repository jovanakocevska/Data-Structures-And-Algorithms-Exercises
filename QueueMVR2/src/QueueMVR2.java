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

class LinkedQueue<E> implements Queue<E> {

	// Redicata e pretstavena na sledniot nacin:
    // length go sodrzi brojot na elementi.
    // Elementite se zachuvuvaat vo jazli dod SLL
    // front i rear se linkovi do prviot i posledniot jazel soodvetno.
    SLLNode<E> front, rear;
    int length;

    // Konstruktor ...

    public LinkedQueue () {
        clear();
    }

    public boolean isEmpty () {
       	// Vrakja true ako i samo ako redicata e prazena.
        return (length == 0);
    }

    public int size () {
    	// Ja vrakja dolzinata na redicata.
        return length;
    }

    public E peek () {
       	// Go vrakja elementot na vrvot t.e. pocetokot od redicata.
        if (front == null)
            throw new NoSuchElementException();
        return front.element;
    }

    public void clear () {
    	// Ja prazni redicata.
        front = rear = null;
        length = 0;
    }

    public void enqueue (E x) {
    	// Go dodava x na kraj od redicata.
        SLLNode<E> latest = new SLLNode<E>(x, null);
        if (rear != null) {
            rear.succ = latest;
            rear = latest;
        } else
            front = rear = latest;
        length++;
    }

    public E dequeue () {
    	// Go otstranuva i vrakja pochetniot element na redicata.
        if (front != null) {
            E frontmost = front.element;
            front = front.succ;
            if (front == null)  rear = null;
            length--;
            return frontmost;
        } else
            throw new NoSuchElementException();
    }

}

interface Queue<E> {

	// Elementi na redicata se objekti od proizvolen tip.

    // Metodi za pristap:

    public boolean isEmpty ();
    	// Vrakja true ako i samo ako redicata e prazena.

    public int size ();
    	// Ja vrakja dolzinata na redicata.

    public E peek ();
    	// Go vrakja elementot na vrvot t.e. pocetokot od redicata.

    // Metodi za transformacija:

    public void clear ();
    	// Ja prazni redicata.

    public void enqueue (E x);
    	// Go dodava x na kraj od redicata.

    public E dequeue ();
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

public class QueueMVR2 {

	public static void MVR(LinkedQueue<Covek> licnaKarta, LinkedQueue<Covek> pasos, LinkedQueue<Covek> vozackaDozvola) {

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

		LinkedQueue<Covek> LicniKarti = new LinkedQueue<Covek>();
		LinkedQueue<Covek> Pasosi = new LinkedQueue<Covek>();
		LinkedQueue<Covek> VozackiDozvoli = new LinkedQueue<Covek>();

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
