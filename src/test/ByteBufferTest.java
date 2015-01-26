package test;

import org.junit.Assert;
import org.junit.Test;

import dataCollections.ByteBufferArray;
import dataCollections.CircularArray;
import dataCollections.CircularByteArray;

public class ByteBufferTest {


	@Test
	public void testCircularArray() {
		CircularArray<String> array = new CircularArray<String>(5);
		array.add("A");
		array.add(new String[] {"B", "C", "D", "E"}, 0, 4);
		array.remove(3);
		array.add("F");
		array.remove(4);
		String[] ary = new String[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new String[0]);

		array.add(new String[] {"G", "H"}, 0, 2);
		ary = new String[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new String[] { "G", "H" });

		array.remove(3);
		array.add("I");
		array.remove(6);
		array.add("J");
		array.add(new String[] {"K", "L", "M"}, 0, 3);
		array.add(null, 0, 1);
		ary = new String[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new String[] { "J", "K", "L", "M" });
	}


	@Test
	public void testCircularByteArray() {
		CircularByteArray array = new CircularByteArray(5);
		array.add(new byte[] {1, 2}, 0, 2);
		array.add(new byte[] {3, 4, 5, 6, 7}, 2, 3);
		array.remove(3);
		array.add(new byte[] {100, 101, 102}, 0, 3);
		array.remove(4);
		array.add(new byte[] {127}, 0, 1);
		byte[] ary = new byte[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new byte[] { 102, 127 });

		array.add(new byte[] {8, 9, 10, 11, 12}, 0, 5);
		ary = new byte[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new byte[] { 102, 127, 8, 9, 10, 11, 12 });

		array.remove(3);
		array.add(new byte[] {1, 2}, 0, 2);
		array.remove(6);
		array.add(new byte[] {3, 4, 5, 6}, 0, 4);
		array.add(new byte[] {7}, 0, 0);
		array.add(null, 0, 1);
		array.add(new byte[] {7}, 0, 1);
		ary = new byte[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new byte[] { 3, 4, 5, 6, 7 });
	}


	@Test
	public void testByteBufferArray() throws Exception {
		ByteBufferArray buf = new ByteBufferArray(16);
		String str1 = "A string longer than 16 bytes";
		byte[] bytesOriginal = {100, 101, 102, 103, 104};
		byte[] bytes = new byte[5];
		int temp = 0;

		try {
			// Write a string
			buf.writeUTF(str1);
			// Reposition to the beginning of the buffer
			buf.position(0);
			// Read the string and ensure it matches the original
			Assert.assertTrue(buf.readUTF().equals(str1));
			// Save the current position
			temp = buf.position();
			// Write 4 integers into the buffer, 16 bytes
			buf.writeInt(-1);
			buf.writeInt(1);
			buf.writeInt(2);
			buf.writeInt(3);
			// Skip back 2 integers
			buf.position(buf.position()-8);
			// Read the second to last integer (which should be 2)
			Assert.assertTrue(buf.readInt() == 2);
			// Check if the position after reading the second to last integer is now three 12 bytes (3 integers) from the
			// original position
			Assert.assertTrue(buf.position() == temp + 12);
			// Write a group of bytes
			buf.write(bytesOriginal);
			// Reposition to the beginning of the byte group and read them again
			buf.position(buf.position()-bytes.length);
			buf.read(bytes, 0, bytes.length);
			// Ensure the read bytes match the original bytes
			for(int i = 0; i < bytesOriginal.length; i++) {
				Assert.assertTrue(bytesOriginal[i] == bytes[i]);
			}
			// Reposition to the beginning of the buffer
			buf.position(0);
			// Ensure the first string and integer written still match
			Assert.assertTrue(buf.readUTF().equals(str1));
			Assert.assertTrue(buf.readInt() == -1);
			// Clear the buffer
			buf.clear();
			// Ensure that trying to position outside of the buffer's range throws an exception
			temp = 0;
			try {
				buf.position(1);
			} catch(Exception e) {
				temp = 1;
			}
			Assert.assertTrue(temp != 0);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			buf.close();
		}
	}

}
