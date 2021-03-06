package answer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.ArrayList;
import java.util.Arrays;


/*
 * This class contains a few simple tests for the bag type.
 * This version has been extended with some additional tests
 * to cover design decisions made in our HashBag implementation.
 * For instance, when asked to add a negative number of items
 * to a bag we have chosen to throw an exception.
 */
public class BagTestExtended {

	Bag<String> stringBag; // a bag of one type
	Bag<Integer> intBag; // a bag of another type

	@Before @Test /* Create two new bags */
	public void setUp() {
		stringBag = new HashBag<String>();
		intBag = new HashBag<Integer>();
	}

	@Test /* One bag, one kind of element */
	public void singleElementType() throws BagException {
		stringBag.add("c", 3);
		stringBag.add("c", 6);
		assertEquals(9, stringBag.quantity("c"));
	}

	@Test /* One bag, multiple kinds of element */
	public void multipleElementTypes() throws BagException {
		stringBag.add("a", 5);
		stringBag.add("b", 3);
		stringBag.add("a", 6);
		stringBag.add("b", 2);
		assertEquals(5, stringBag.quantity("b"));
		assertEquals(11, stringBag.quantity("a"));
	}

	@Test /* Adding and then removing elements */
	public void removingElements() throws BagException {
		stringBag.add("x", 17);
		stringBag.add("y", 3);
		stringBag.remove("x", 1);
		stringBag.add("x", 6);
		stringBag.remove("x", 2);
		assertEquals(20, stringBag.quantity("x"));
	}

	@Test /* Multiple bags */
	public void multipleBags() throws BagException {
		intBag.add(86, 5);
		stringBag.add("m", 12);
		stringBag.remove("m", 3);
		intBag.add(86, 8);
		intBag.add(99, 5);
		intBag.add(99, 5);
		assertEquals(9, stringBag.quantity("m"));
		assertEquals(13, intBag.quantity(86));
		assertEquals(10, intBag.quantity(99));
	}

	@Test /* Total size of a bag */
	public void totalSizeEqualsSumOfQuantities() throws BagException {
		stringBag.add("j", 33);
		stringBag.add("k", 1);
		stringBag.remove("j", 10);
		stringBag.add("i", 6);
		stringBag.remove("i", 2);
		stringBag.add("k", 6);
		assertEquals(stringBag.quantity("i") + stringBag.quantity("j") + 
		             stringBag.quantity("k"), stringBag.size());
	}

	@Test /* Removing all elements */
	public void removeAll() throws BagException {
		stringBag.add("u", 6);
		stringBag.add("v", 4);
		stringBag.add("u", 2);
		stringBag.remove("u", 8);
		assertEquals(0, stringBag.quantity("u"));
	}

	@Test /* Nonexistent elements */
	public void nonexistent() throws BagException {
		stringBag.add("f", 3);
		stringBag.add("g", 2);
		assertEquals(0, stringBag.quantity("h"));
	}

	@Test /* Adding nothing */
	public void addNothing() throws BagException {
		stringBag.add("p", 5);
		stringBag.add("p", 0);
		assertEquals(5, stringBag.quantity("p"));
	}
	
	@Test /* Removing nothing */
	public void removeNothing() throws BagException {
		stringBag.add("p", 5);
		stringBag.remove("p", 0);
		assertEquals(5, stringBag.quantity("p"));
	}
	
	@Test /* Distinct elements in a bag */
	public void distinctElements() throws BagException {
		stringBag.add("t", 33);
		stringBag.add("s", 4);
		stringBag.add("t", 6);
		assertEquals(2, stringBag.toSet().size());
		assertTrue(stringBag.toSet().contains("s"));
		assertTrue(stringBag.toSet().contains("t"));
	}
	
	@Test /* Iterating over the elements in a bag */
	public void elementIteration() throws BagException {
		ArrayList<String> valuesProduced = new ArrayList<String>();
		Object[] sortedValues;
		stringBag.add("r", 1);
		stringBag.add("q", 3);
		stringBag.add("r", 2);
		stringBag.add("q", 1);
		stringBag.add("p", 1);
		for (String element : stringBag)
			valuesProduced.add(element);
		sortedValues = valuesProduced.toArray();
		Arrays.sort(sortedValues);
		assertArrayEquals(new String[] {"p", "q", "q", "q", "q", "r", "r", "r"}, 
		                  sortedValues);
	}

	
	/* Additional tests added to cover certain API design decisions */
	
	@Test(expected = BagException.class) /* Attempt to remove too many elements */
	public void removeTooMany() throws BagException {
		stringBag.add("e", 34);
		stringBag.remove("e", 35);
		// Should throw a BagException, which is the expected test result.
		// No test assertions are required.
	}

	@Test(expected = BagException.class) /* Attempt to remove negative elements */
	public void removeNegative() throws BagException {
		stringBag.add("e", 12);
		stringBag.remove("e", -2);
	}
	
	@Test(expected = BagException.class) /* Attempt to add negative elements */
	public void addNegative() throws BagException {
		stringBag.add("e", -2);
	}
	
}
