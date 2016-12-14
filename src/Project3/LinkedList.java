package Project3;

import java.util.Iterator;


public class LinkedList<T> extends java.lang.Object implements Iterable<T> {

	protected class Node {
		private T data;
		private Node next;

		private Node(T data) {
			this.data = data;
			next = null; 
		}
	}

	/* List head pointer */
	protected Node head,tail;
	protected int size;

	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	/* Add to end */
	public LinkedList<T> addToEnd(T data) {
		if (size == 0) {
			Node n = new Node(data);
			n.next = head;
			head = n;
			tail = n;
			size++;
		} else {
			Node n = new Node(data);

			Node curr = head;
			while (curr.next != null) {
				curr = curr.next;
			}
			curr.next = n;
			tail = n;
			size++;
		}
		return this;
	}

	/* Adding at the front of the list */
	public LinkedList<T> addToFront(T data) {
		if (head == null) {
			Node n = new Node(data);
			n.next = head;
			head = n;
			tail = n;
			size++;
		} else {
			Node n = new Node(data);
			n.next = head;
			head = n;
			size++;
		}
		return this;
	}

	//Returns first element 
	public T getFirst() {
		if (head == null){
			return null;
		}
		return head.data;
	}

	//Returns last element 
	public T getLast() {
		if (tail == null){
			return null;
		}
		return tail.data;
	}

	//returns size
	public int getSize() {
		return size;
	}


	//Iterates over all elemenets in list 
	public Iterator<T> iterator() {
		return new iterator();
	}

	public class iterator implements Iterator<T> {

		Node curr = head;
		Node prev = head;


		public boolean hasNext() {
			if (curr == null)
				return false;
			return true;
		}

		public T next() {
				prev = curr;
				curr = curr.next;
				return prev.data;
			
		}

		 public void remove(){
			 throw new UnsupportedOperationException();
		}

	}

	//removes all instances of the target data 
	public LinkedList<T> removeAllInstances(T targetData) {
		if (size == 1 && head.data.equals(targetData)){
			head = null;
			tail = null;
			size--;
		}
		
		Node curr = head;
		Node prev = null;
		while (curr != null) {
			if (curr.data.equals(targetData)) {
				if (curr == head) {
					Node n = new Node(curr.data);
					head = curr.next;
					size--;

				} else {
					Node n = new Node(curr.data);
					prev.next = curr.next;
					size--;
				}
			}
			else {
				prev = curr;
			}
			curr = curr.next;
		}
		return this;
	}

	//Removes and returns firs element in the list 
	public T retrieveFirstElement() {
		if (head == null) {
			return null;
		}

		if (size == 1) {
			Node n = new Node(head.data);
			head = null;
			tail = null;
			size--;
			return n.data;
		} else {
			Node n = new Node(head.data);
			head = head.next;
			size--;
			return n.data;
		}
	}
	
	//Removes and returns last element 
	public T retrieveLastElement() {
		if (tail == null) {
			return null;
		}
		if (size == 1) {
			Node n = new Node(head.data);
			head = null;
			tail = null;
			size--;
			return n.data;
		} else {
			Node curr = head;
			Node prev = null;
			while (curr.next != null) {
				prev = curr;
				curr = curr.next;
			}

			Node toReturn = new Node(tail.data);
			tail = prev;
			prev.next = null;
			size--;
			return toReturn.data;

		}
	}

}
