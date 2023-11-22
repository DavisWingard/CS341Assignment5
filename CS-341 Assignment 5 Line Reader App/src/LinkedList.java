

public class LinkedList{
	Node head;
	
	public void add(int num) {
		Node node = new Node(num);
		node.num = num;
		node.next = null;
		
		//check if list is empty
		if (head == null) {
			head = node;
		}
		//list is not empty, find end of list and add node to it
		else {
			Node n = head;
			while (n.next != null) {
				n = n.next;
			}
			n.next = node;
		}
	}
	
	//print method for testing
	public void print() {
		Node node = head;
		while (node.next != null) {
			System.out.println(node.num);
			node = node.next;
		}
		//print out last element bc while loop cuts it off
		System.out.println(node.num);
	}
}
