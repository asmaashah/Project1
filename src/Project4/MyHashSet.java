package Project4;

import java.util.Iterator;

//import MyHashSet.Node;
//import MyHashSet.iterator;

public class MyHashSet<T> implements Iterable<T> {

	/**
	 * Unless otherwise specified, the table will start as an array of this
	 * length.
	 */

	public final static int DEFAULT_INITIAL_CAPACITY = 4;

	/**
	 * When the ratio of size/capacity reaches or exceeds this value, it is time
	 * for the table to be expanded
	 */

	public final static double LOAD_FACTOR = 0.75;


	public Node<T>[] table;
	private int size; // number of elements in the table



	public static class Node<T> {
		private T data;
		public Node<T> next; 

		private Node(T data) {
			this.data = data;
			next = null;
		}

		@SuppressWarnings("unchecked")
		public static <T> Node<T>[] makeArray(int size) {
			return new Node[size];
		}
	}

	/**
	 * Initializes an empty table of the specified length (capacity). Relies on
	 * the static makeArray method of the Node class.
	 * 
	 * @param initialCapacity
	 *            initial length (capacity) of table
	 */
	public MyHashSet(int initialCapacity) {
		table = Node.makeArray(initialCapacity);

	}

	/**
	 * Initializes an empty table of length equal to DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		table = Node.makeArray(DEFAULT_INITIAL_CAPACITY);
	}
	
	//returns the bucket a specified element is supposed to go into
	public int getBucket(T element, int capacity) {
		return Math.abs(103 * element.hashCode()) % capacity;
	}

	/**
	 * Returns the number of elements stored in the table.
	 * 
	 * @return number of elements in the table
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * 
	 * @return length of the table (capacity)
	 */
	public int getCapacity() {
		return table.length;
	}

	/**
	 * Looks for the specified element in the table.
	 * 
	 * @param element
	 *            to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(T element) {
		int bucket = getBucket(element, this.getCapacity());
		if (table[bucket] == null) {
			return false;
		}
		Node<T> curr = table[bucket];
		while (curr != null) {
			if (curr.data.equals(element)) {
				return true;
			}
			curr = curr.next;
		}

		return false;

	}

	/**
	 * Adds the specified element to the collection.
	 * 
	 * @param element
	 *            the element to be added to the collection
	 */
	public void add(T element) {
		if (this.contains(element)) {
			return;
		} else {
			size++;
			Node<T> toAdd = new Node<T>(element);
			toAdd.next = table[getBucket(element, this.getCapacity())];
			table[getBucket(element, this.getCapacity())] = toAdd;

			double loadFactor = (double) this.size / (double) this.getCapacity();
			if (loadFactor >= LOAD_FACTOR) {
				Node<T>[] newTable = Node.makeArray(table.length * 2);
				for (int i = 0; i < table.length; i++) {
					Node<T> curr = table[i];
					while (curr != null) {
						Node<T> add = new Node<T>(curr.data);
						add.next = newTable[getBucket(add.data, newTable.length)];
						newTable[getBucket(add.data, newTable.length)] = add;
						curr = curr.next;
					}
				}
				table = newTable;
			}
		}
	}

	/**
	 * Removes the specified element from the collection. 
	 *
	 * @param element
	 *            the element to be removed
	 */
	public void remove(T element) {
		if (this.contains(element)) {
			size--;
			int bucketLocation = this.getBucket(element, this.getCapacity());
			Node<T> curr = table[bucketLocation];
			Node<T> prev = null;

			if (curr.data.equals(element)) {
				table[bucketLocation] = table[bucketLocation].next;
				return;
			}

			prev = table[bucketLocation];
			curr = prev.next;
			while (curr != null) {
				if (curr.data.equals(element)) {
					prev.next = curr.next;
					return;
				}
				prev = curr;
				curr = curr.next;
			}
		}
	}

	/**
	 * Returns an Iterator that can be used to iterate over all of the elements
	 * in the collection.
	 */
	public Iterator<T> iterator() {
		return new iterator();
	}

	public class iterator implements Iterator<T> {

		int theSize = getSize();

		int elementsFound = 0;
		int counted = 0;

		Node<T> curr = table[counted];
		Node<T> prev = null;

		public boolean hasNext() {
			if (elementsFound < getSize())
				return true;
			return false;
		}

		public T next() {
			while (curr == null) {
				counted++;
				curr = table[counted];
			}

			prev = curr;
			curr = curr.next;
			elementsFound++;
			return prev.data;

		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}
