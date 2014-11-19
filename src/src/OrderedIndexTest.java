package src;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class OrderedIndexTest {
	
	@Test
	public void testDeletionOfIndexWithOneElement() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		testIndex.addRecord(new IndexRecord("10", 0));
		assertEquals("10", testIndex.getRoot().getData());
		
		testIndex.deleteRecord("10");
		assertNull(testIndex.getRoot());
	}
	
	@Test
	public void testAddingRecords() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		testIndex.addRecord(new IndexRecord("10", 0));
		assertTrue(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().rightChildIsAThread());
		assertEquals("10", testIndex.getRoot().getLeftChild().getData());
		assertEquals("10", testIndex.getRoot().getRightChild().getData());
		
		testIndex.addRecord(new IndexRecord("05", 0));
		assertFalse(testIndex.getRoot().leftChildIsAThread());
		assertEquals("05", testIndex.getRoot().getLeftChild().getData());
		assertTrue(testIndex.getRoot().getLeftChild().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getLeftChild().rightChildIsAThread());
		assertEquals("05", testIndex.getRoot().getLeftChild().getLeftChild().getData());
		assertEquals("10", testIndex.getRoot().getLeftChild().getRightChild().getData());

		testIndex.addRecord(new IndexRecord("03", 0));
		assertFalse(testIndex.getRoot().getLeftChild().leftChildIsAThread());
		assertEquals("03", testIndex.getRoot().getLeftChild().getLeftChild().getData());
		assertTrue(testIndex.getRoot().getLeftChild().getLeftChild().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getLeftChild().getLeftChild().rightChildIsAThread());
		assertEquals("03", testIndex.getRoot().getLeftChild().getLeftChild().getLeftChild().getData());
		assertEquals("05", testIndex.getRoot().getLeftChild().getLeftChild().getRightChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithNoChildrenAsLeftChild() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		testIndex.addRecord(new IndexRecord("10", 0));
		testIndex.addRecord(new IndexRecord("05", 1));
		testIndex.deleteRecord("05");
		
		assertTrue(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().rightChildIsAThread());
		assertEquals("10", testIndex.getRoot().getLeftChild().getData());
		assertEquals("10", testIndex.getRoot().getRightChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithNoChildrenAsRightChild() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();

		testIndex.addRecord(new IndexRecord("10", 0));
		testIndex.addRecord(new IndexRecord("15", 0));
		testIndex.deleteRecord("15");
		
		assertTrue(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().rightChildIsAThread());
		assertEquals("10", testIndex.getRoot().getLeftChild().getData());
		assertEquals("10", testIndex.getRoot().getRightChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithLeftChildWhereRecordToDeleteIsALeftChild() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		//recordToDelete is leftChild
		testIndex.addRecord(new IndexRecord("10", 0));
		testIndex.addRecord(new IndexRecord("05", 1));
		testIndex.addRecord(new IndexRecord("03", 2));
		testIndex.deleteRecord("05");
		
		assertFalse(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getLeftChild().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getLeftChild().rightChildIsAThread());
		assertEquals("03", testIndex.getRoot().getLeftChild().getLeftChild().getData());
		assertEquals("10", testIndex.getRoot().getLeftChild().getRightChild().getData());
		assertEquals("03", testIndex.getRoot().getLeftChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithLeftChildWhereRecordToDeleteIsARightChild() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		//recordToDelete is rightChild
		testIndex.addRecord(new IndexRecord("10", 0));
		testIndex.addRecord(new IndexRecord("15", 1));
		testIndex.addRecord(new IndexRecord("12", 2));
		testIndex.deleteRecord("15");
		
		assertFalse(testIndex.getRoot().rightChildIsAThread());
		assertTrue(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getRightChild().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getRightChild().rightChildIsAThread());
		assertEquals("10", testIndex.getRoot().getRightChild().getLeftChild().getData());
		assertEquals("12", testIndex.getRoot().getRightChild().getRightChild().getData());
		assertEquals("12", testIndex.getRoot().getRightChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithRightChildWhereRecordToDeleteIsARightChild() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		//recordToDelete is rightChild
		testIndex.addRecord(new IndexRecord("10", 0));
		testIndex.addRecord(new IndexRecord("15", 1));
		testIndex.addRecord(new IndexRecord("17", 2));
		testIndex.deleteRecord("15");
		
		assertFalse(testIndex.getRoot().rightChildIsAThread());
		assertTrue(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getRightChild().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getRightChild().rightChildIsAThread());
		assertEquals("10", testIndex.getRoot().getRightChild().getLeftChild().getData());
		assertEquals("17", testIndex.getRoot().getRightChild().getRightChild().getData());
		assertEquals("17", testIndex.getRoot().getRightChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithRightChildWhereRecordToDeleteIsALeftChild() throws Exception {
		OrderedIndex testIndex = new OrderedIndex();
		
		//recordToDelete is leftChild
		testIndex.addRecord(new IndexRecord("10", 0));
		testIndex.addRecord(new IndexRecord("05", 1));
		testIndex.addRecord(new IndexRecord("07", 2));
		testIndex.deleteRecord("05");
		
		assertFalse(testIndex.getRoot().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getLeftChild().leftChildIsAThread());
		assertTrue(testIndex.getRoot().getLeftChild().rightChildIsAThread());
		assertEquals("07", testIndex.getRoot().getLeftChild().getLeftChild().getData());
		assertEquals("10", testIndex.getRoot().getLeftChild().getRightChild().getData());
		assertEquals("07", testIndex.getRoot().getLeftChild().getData());
	}
	
	@Test
	public void testDeletionOfRecordWithTwoChildrenAsALeftChild() throws Exception {
		Assert.fail();
	}
	
	@Test
	public void testDeletionOfRecordWithTwoChildrenAsARightChild() throws Exception {
		Assert.fail();
	}
	
	@Test
	public void testToDoList() throws Exception {
		/*
		 * Remove To Do List from OrderedIndex delete() method
		 * Implement Deletion of Records with Two Children
		 * Check that management of Head, Tail, and Root pointers are correct for all deletions
		 * Confirm printing works correctly
		 * Check deletion being dependent on unique names (check databaseIndex too to verify)
		 * Close Keyboard to remove warnings
		 * Change database.txt back
		 */
		Assert.fail();
	}
	
}
