class Key implements Comparable<Key> {
	int key;

	public Key(int key) {
		super();
		this.key = key;
	}

	@Override
	public int compareTo(Key arg0) {
		// TODO Auto-generated method stub
		return (key - arg0.key);
	}

	@Override
	public int hashCode() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (key != other.key)
			return false;
		return true;
	}

}

class KeyTwo implements Comparable<KeyTwo> {
	String ime, prezime;

	public KeyTwo(String ime, String prezime) {
		super();
		this.ime = ime;
		this.prezime = prezime;
	}

	@Override
	public int compareTo(KeyTwo o) {
		// TODO Auto-generated method stub
		if (ime.compareTo(o.ime) == 0)
			return prezime.compareTo(o.prezime);
		return ime.compareTo(o.ime);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((prezime == null) ? 0 : prezime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyTwo other = (KeyTwo) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (prezime == null) {
			if (other.prezime != null)
				return false;
		} else if (!prezime.equals(other.prezime))
			return false;
		return true;
	}

}

public class HashTestKey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
