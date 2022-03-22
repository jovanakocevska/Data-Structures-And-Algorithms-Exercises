import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

class SLLNode {
    String index;
    String name;
    int points;
    SLLNode succ;

    public SLLNode(String index, String name, int points, SLLNode succ) {
        this.index = index;
        this.name = name;
        this.points = points;
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

    public void insertFirst(String index, String name, int points) {
        SLLNode ins = new SLLNode(index, name, points, first);
        first = ins;
    }

    public void insertLast(String index, String name, int points) {
        if (first != null) {
            SLLNode tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            SLLNode ins = new SLLNode(index, name, points, null);
            tmp.succ = ins;
        } else {
            insertFirst(index, name, points);
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
			if(first ==node){
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

public class Student {

	public static void removeStudent(SLL students) {
		// todo: implement function
		
		SLLNode najmal = students.first;
		SLLNode tmp = students.first;
		
		while (tmp != null) {
			if (tmp.points < najmal.points) {
				najmal = tmp;
			}
			tmp = tmp.succ;
		}
		
		students.delete(najmal);
		
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		SLL students = new SLL();

		for (int i = 0; i < n; i++) {
			String line = scanner.nextLine();
			String[] parts = line.split("\\s+");
			students.insertLast(parts[0], parts[1], Integer.parseInt(parts[2]));
		}

		removeStudent(students);
		System.out.println(students.toString());
	}
}
