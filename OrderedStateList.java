//package edu.iastate.cs472.proj1;
//
//import java.util.Iterator;
//import java.util.NoSuchElementException;
//
///**
// *
// * @author Abhay Prasanna Rao
// *
// */
//
///**
// * This class describes a circular doubly-linked list of states to represent both the OPEN and CLOSED lists
// * used by the A* algorithm.  The states on the list are sorted in the
// *
// *     a) order of non-decreasing cost estimate for the state if the list is OPEN, or
// *     b) lexicographic order of the state if the list is CLOSED.
// *
// */
//public class OrderedStateList
//{
//
//	/**
//	 * Implementation of a circular doubly-linked list with a dummy head node.
//	 */
//	  private State head;           // dummy node as the head of the sorted linked list
//	  private int size = 0;
//
//	  private boolean isOPEN;       // true if this OrderedStateList object is the list OPEN and false
//	                                // if the list CLOSED.
//
//	  /**
//	   *  Default constructor constructs an empty list. Initialize heuristic. Set the fields next and
//	   *  previous of head to the node itself. Initialize instance variables size and heuristic.
//	   *
//	   * @param h
//	   * @param isOpen
//	   */
//	  public OrderedStateList(Heuristic h, boolean isOpen)
//	  {
//		  // Create an empty state for the dummy head node
//		  this.head = new State(new int[][]{{0,0,0}, {0,0,0}, {0,0,0}});
//		  this.head.next = this.head;
//		  this.head.previous = this.head;
//		  this.size = 0;
//		  this.isOPEN = isOpen;
//		  State.heu = h;   // initialize heuristic used for evaluating all State objects.
//	  }
//
//
//	  public int size()
//	  {
//		  return size;
//	  }
//
//
//	  /**
//	   * A new state is added to the sorted list.  Traverse the list starting at head.  Stop
//	   * right before the first state t such that compareStates(s, t) <= 0, and add s before t.
//	   * If no such state exists, simply add s to the end of the list.
//	   *
//	   * Precondition: s does not appear on the sorted list.
//	   *
//	   * @param s
//	   */
//
//	  public void addState(State s) {
//		  State current = head.next;
//		  while (current != head && compareStates(s, current) > 0) {
//			  current = current.next;
//		  }
//		  s.next = current;
//		  s.previous = current.previous;
//		  current.previous.next = s;
//		  current.previous = s;
//		  size++;
//	  }
//
//
//	  /**
//	   * Conduct a sequential search on the list for a state that has the same board configuration
//	   * as the argument state s.
//	   *
//	   * Calls equals() from the State class.
//	   *
//	   * @param s
//	   * @return the state on the list if found
//	   *         null if not found
//	   */
//	  public State findState(State s)
//	  {
//		  State t = head.next;
//		  do {
//			  if (t.equals(s)) {
//				  return t;
//			  }
//			  t = t.next;
//		  } while (t != head && compareStates(s, t) >= 0);
//		  return null;
//	  }
//
//
//	  /**
//	   * Remove the argument state s from the list.  It is used by the A* algorithm in maintaining
//	   * both the OPEN and CLOSED lists.
//	   *
//	   * @param s
//	   * @throws IllegalStateException if s is not on the list
//	   */
//	  public void removeState(State s) throws IllegalStateException
//	  {
//		  State t = findState(s);
//		  if (t == null) {
//			  throw new IllegalStateException();
//		  } else {
//			  t.next.previous = t.previous;
//			  t.previous.next = t.next;
//			  size--;
//		  }
//	  }
//
//
//	  /**
//	   * Remove the first state on the list and return it.  This is used by the A* algorithm in maintaining
//	   * the OPEN list.
//	   *
//	   * @return
//	   */
//	  public State remove()
//	  {
//		  if (size > 0) {
//			  State t = head.next;
//			  t.next.previous = head;
//			  head.next = t.next;
//			  size--;
//			  return t;
//		  }
//		  return null;
//	  }
//
//
//	  /**
//	   * Compare two states depending on whether this OrderedStateList object is the list OPEN
//	   * or the list CLOSE used by the A* algorithm.  More specifically,
//	   *
//	   *     a) call the method compareTo() of the State if isOPEN == true, or
//	   *     b) create a StateComparator object to call its compare() method if isOPEN == false.
//	   *
//	   * @param s1
//	   * @param s2
//	   * @return -1 if s1 is less than s2 as determined by the corresponding comparison method
//	   *         0  if they are equal
//	   *         1  if s1 is greater than s2
//	   */
//	  private int compareStates(State s1, State s2)
//	  {
//		  if (isOPEN) {
//			  return s1.compareTo(s2);
//		  } else {
//			  StateComparator comparator = new StateComparator();
//			  return comparator.compare(s1, s2);
//		  }
//	  }
//}

package edu.iastate.cs472.proj1;

import java.util.NoSuchElementException;

/**
 * @author umeshsaitejapoola
 */

public class OrderedStateList {

	private State head;  // dummy node as the head of the sorted linked list
	private int size = 0;
	private boolean isOPEN; // true if this is the OPEN list, false if CLOSED

	/**
	 * Default constructor constructs an empty list. Initialize heuristic.
	 *
	 * @param h
	 * @param isOpen
	 */
	public OrderedStateList(Heuristic h, boolean isOpen) {
		this.head = new State(new int[3][3]); // dummy node
		this.head.previous = this.head;
		this.head.next = this.head;
		this.isOPEN = isOpen;
		State.heu = h; // initialize heuristic for evaluating all State objects
		this.size = 0;
	}

	public int size() {
		return size;
	}

	/**
	 * Adds a new state to the sorted list based on the appropriate heuristic.
	 *
	 * @param s the state to add
	 */
	public void addState(State s) {
		State current = head.next;
		while (current != head && compareStates(s, current) > 0) {
			current = current.next;
		}
		insertBefore(current, s);
	}

	/**
	 * Helper method to insert a state before another state.
	 *
	 * @param target the target state
	 * @param toInsert the state to insert
	 */
	private void insertBefore(State target, State toInsert) {
		toInsert.next = target;
		toInsert.previous = target.previous;
		target.previous.next = toInsert;
		target.previous = toInsert;
		size++;
	}

	/**
	 * Check if the list contains a state with the same board configuration as the given state.
	 *
	 * @param s the state to check
	 * @return true if the state is found, false otherwise
	 */
	public boolean contains(State s) {
		State current = head.next;
		while (current != head) {
			if (current.equals(s)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	/**
	 * Check if the list is empty.
	 *
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * Removes a state from the list.
	 *
	 * @param s the state to remove
	 */
	public void removeState(State s) {
		State current = findState(s);
		if (current != null) {
			current.previous.next = current.next;
			current.next.previous = current.previous;
			size--;
		}
	}

	/**
	 * Removes and returns the first state in the list.
	 *
	 * @return the first state
	 */
	public State remove() {
		if (size == 0) {
			throw new NoSuchElementException("List is empty.");
		}
		State first = head.next;
		removeState(first);
		return first;
	}

	/**
	 * Finds a state in the list with the same board configuration as the given state.
	 *
	 * @param s the state to find
	 * @return the found state, or null if not found
	 */
	public State findState(State s) {
		State current = head.next;
		while (current != head) {
			if (current.equals(s)) {
				return current;
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Compares two states.
	 *
	 * @param s1 the first state
	 * @param s2 the second state
	 * @return comparison result based on whether this is an OPEN or CLOSED list
	 */
	private int compareStates(State s1, State s2) {
		if (isOPEN) {
			return s1.compareTo(s2); // Compare by cost in OPEN list
		} else {
			StateComparator comparator = new StateComparator();
			return comparator.compare(s1, s2); // Lexicographic comparison in CLOSED list
		}
	}
}