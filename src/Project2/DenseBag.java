package Project2;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

//import DenseBag.myIterator;

/**
 * <P>
 * The DenseBag class implements a Set-like collection that allows duplicates (a
 * lot of them).
 * </P>
 * <P>
 * In a Bag, removing an item removes a single instance of the item. For
 * example, a Bag b could contain additional instances of the String "a" even
 * after calling b.remove("a").
 * </P>
 * <P>
 * The iterator for a dense bag must iterate over all instances, including
 * duplicates. In other words, if a bag contains 5 instances of the String "a",
 * an iterator will generate the String "a" 5 times.
 * </P>
 * <P>
 * In addition to the methods defined in the Collection interface, the DenseBag
 * class supports several additional methods: uniqueElements, getCount, and
 * choose.
 * </P>
 * <P>
 * The class extends AbstractCollection in order to get implementations of
 * addAll, removeAll, retainAll and containsAll.
 * </P>
 */

public class DenseBag<T> extends AbstractCollection<T> implements Serializable {

	/* Leave this here! (We need it for our testing) */
	private static final long serialVersionUID = 1L;

	/* Create whatever instance variables you think are good choices */

	public Map<T, Integer> items;
	int size;

	/**
	 * Initialize a new, empty DenseBag
	 */
	public DenseBag() {
		items = new HashMap<T, Integer>();
		size = 0;

	}

	/**
	 * Adds an instance of o to the Bag
	 * 
	 * @return always returns true, since added an element to a bag always
	 *         changes it
	 * 
	 */
	@Override
	public boolean add(T o) {
		if (items.containsKey(o)) {
			int curr = items.get(o);
			curr++;
			items.put(o, curr);
		} else {
			int one = 1;
			items.put(o, one);
		}
		size++;
		return true;
	}

	/**
	 * Adds multiple instances of o to the Bag. If count is less than 0 or count
	 * is greater than 1 billion, throws an IllegalArgumentException.
	 * 
	 * @param o
	 *            the element to add
	 * @param count
	 *            the number of instances of o to add
	 * @return true, since addMany always modifies the DenseBag.
	 */
	public boolean addMany(T o, int count) {
		if (count < 0 || count > 1000000000) {
			throw new IllegalArgumentException();
		}
		if (items.containsKey(o)) {
			int curr = items.get(o);
			curr += count;
			items.put(o, curr);
		} else {
			int num = count;
			items.put(o, num);
		}
		size += count;
		return true;

	}

	/**
	 * Generate a String representation of the DenseBag.
	 */
	@Override
	public String toString() {
		StringBuilder theStuff = new StringBuilder();
		for (T theKey : items.keySet()) {
			theStuff.append(theKey + ": ");
			theStuff.append(items.get(theKey) + " ");
			theStuff.append("[Next Key]");
		}

		return theStuff.toString();
	}

	/**
	 * Tests if two DenseBags are equal. Two DenseBags are considered equal if
	 * they contain the same number of copies of the same elements. 
	 */
	@Override
	public boolean equals(Object o) {
		if (items == o) {
			return true;
		}
		if (!(o instanceof DenseBag)) {
			return false;
		}

		DenseBag<T> tocomp = (DenseBag<T>) o;

		for (T theKey : items.keySet()) {
			if (tocomp.contains(theKey) == false) {
				return false;
			}
			if (!(tocomp.items.get(theKey).equals(items.get(theKey)))) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Return a hashCode that fulfills the requirements for hashCode (such as
	 * any two equal DenseBags must have the same hashCode) as well as desired
	 * properties (two unequal DenseBags will generally, but not always, have
	 * unequal hashCodes).
	 */
	@Override
	public int hashCode() {
		int code = 0;
		for (T theKeys : items.keySet()) {
			code += theKeys.hashCode() * items.get(theKeys) * size;
		}

		return code;
	}

	/**
	 * <P>
	 * Returns an iterator over the elements in a dense bag. 
	 * </P>
	 */
	@Override
	public Iterator<T> iterator() {
		return new myIterator();
	}

	class myIterator implements Iterator<T> {
		int count = size; // keeps track of which item in the bag you're on
		T curr; // keeps track of what to print
		int numInCurrKey; // keeps track of how many times you've printed curr
		int at = 0; // keeps track of location in the array
		ArrayList<T> theKeys = new ArrayList<T>();// has all the keys

		public myIterator() {
			for (T aKey : items.keySet()) {
				theKeys.add(aKey);
			} // copies keys into array
			if (size > 0) {
				curr = theKeys.get(0);
				numInCurrKey = items.get(curr);
			} // if there are keys in the bag, set the first key
		}

		public boolean hasNext() {
			return count > 0;
		}

		public T next() {
			count--;// since this will return something, count goes down
			if (numInCurrKey > 0) {
				numInCurrKey--;
				return curr;
			} // if you haven't printed all the instances of curr, keep
				// going
			else {
				at++;
				curr = theKeys.get(at);
				numInCurrKey = items.get(curr) - 1;
				return curr;
			} // get the next key, return it, set the number of times it needs
				// to be printed

		}

		public void remove() {
			size--;
			if (numInCurrKey == 1) {
				items.remove(curr);
				at++;
				curr = theKeys.get(at);
				numInCurrKey = items.get(curr) - 1;

			} // if there's only one, get the next key
			count--;
			numInCurrKey--;
			items.remove(curr);

		}
	}

	public T getFirst() {
		return items.keySet().iterator().next();
	}

	/**
	 * return a Set of the elements in the Bag 
	 * 
	 * @return A set of elements in the Bag
	 */
	public Set<T> uniqueElements() {
		return items.keySet();
	}

	/**
	 * Return the number of instances of a particular object in the bag. Return
	 * 0 if it doesn't exist at all.
	 * 
	 * @param o
	 *            object of interest
	 * @return number of times that object occurs in the Bag
	 */
	public int getCount(Object o) {
		if (items.containsKey(o) == false) {
			return 0;
		}
		return (int) items.get(o);
	}

	/**
	 * Given a random number generator, randomly choose an element from the Bag
	 * according to the distribution of objects in the Bag (e.g., if a Bag
	 * contains 7 a's and 3 b's, then 70% of the time choose should return an a,
	 * and 30% of the time it should return a b.
	 * 
	 * 
	 * @param r
	 *            Random number generator
	 * @return randomly chosen element
	 */
	public T choose(Random r) {
		// makes an array of size 1000, goes through the keys and fills the
		// array with the correct proportions of keys then selects a random one
		// from the array
		ArrayList<T> allTheThings = new ArrayList<T>(1000);
		for (T aThing : items.keySet()) {
			for (int i = 0; i < (((double) items.get(aThing) / size) * 1000); i++) {
				allTheThings.add(aThing);
			}
		}
		return allTheThings.get(r.nextInt(allTheThings.size()));
	}

	/**
	 * Returns true if the Bag contains one or more instances of o
	 */
	@Override
	public boolean contains(Object o) {
		if (items.containsKey(o) && items.get(o) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Decrements the number of instances of o in the Bag.
	 * 
	 * @return return true if and only if at least one instance of o exists in
	 *         the Bag and was removed.
	 */

	@Override
	public boolean remove(Object o) {
		if (items.containsKey(o) && items.get(o) > 0) {
			int curr = items.get(o);
			curr--;
			if (curr > 0) {
				items.put((T) o, curr);

			}
			if (curr == 0) {
				items.remove(o);
			}
			size--;
			return true;
		}
		return false;
	}

	/**
	 * Total number of instances of any object in the Bag (counting duplicates)
	 */
	@Override
	public int size() {
		return size;
	}
}
