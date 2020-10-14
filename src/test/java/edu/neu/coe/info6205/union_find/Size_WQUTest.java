package edu.neu.coe.info6205.union_find;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class Size_WQUTest {
	
	 /**
    *
    */
   @Test
   public void testFind0() {
       Depth_WQU h = new Depth_WQU(10);
       assertEquals(0, h.find(0));
   }

   /**
    *
    */
   @Test
   public void testFind1() {
       Depth_WQU h = new Depth_WQU(10);
       h.union(0, 1);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
   }

   /**
    *
    */
   @Test
   public void testFind2() {
       Depth_WQU h = new Depth_WQU(10);
       h.union(0, 1);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
       h.union(2, 1);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
       assertEquals(0, h.find(2));
   }

   /**
    *
    */
   @SuppressWarnings("Duplicates")
   @Test
   public void testFind3() {
       Depth_WQU h = new Depth_WQU(10);
       h.union(0, 1);
       h.union(0, 2);
       h.union(3, 4);
       h.union(3, 5);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
       assertEquals(0, h.find(2));
       assertEquals(3, h.find(3));
       assertEquals(3, h.find(4));
       assertEquals(3, h.find(5));
       h.union(0, 3);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
       assertEquals(0, h.find(2));
       assertEquals(0, h.find(3));
       assertEquals(0, h.find(4));
       assertEquals(0, h.find(5));
   }

   /**
    * TODO this appears to be identical to testFind3. What's going on here?
    */
   @SuppressWarnings("Duplicates")
   @Test
   public void testFind4() {
       Depth_WQU h = new Depth_WQU(10);
       h.union(0, 1);
       h.union(0, 2);
       h.union(3, 4);
       h.union(3, 5);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
       assertEquals(0, h.find(2));
       assertEquals(3, h.find(3));
       assertEquals(3, h.find(4));
       assertEquals(3, h.find(5));
       h.union(0, 3);
       assertEquals(0, h.find(0));
       assertEquals(0, h.find(1));
       assertEquals(0, h.find(2));
       assertEquals(0, h.find(3));
       assertEquals(0, h.find(4));
       assertEquals(0, h.find(5));
   }

   /**
    *
    */
   @Test
   public void testConnected01() {
       Size_WQU h = new Size_WQU(10);
       assertFalse(h.connected(0, 1));
   }
	

}
