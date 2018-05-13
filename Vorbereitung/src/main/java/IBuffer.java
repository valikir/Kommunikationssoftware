
/**
 * <pre>
 * Interface to implement a buffer, 
 * which holds elements of type integer.
 * </pre>
 */
public interface IBuffer {
	/**
	 * Inserts an element if possible
	 * 
	 * @param value Adds one element into the buffer
	 */
	public void add(int value);

	/**
	 * Reads an element if possible
	 * 
	 * @return one element from the buffer
	 */
	public int get();

	/**
	 * Gets the maximum number of elements to save in the buffer
	 * 
	 * @return maximum size of buffer
	 */
	public int getLength();

	/**
	 * Gets actual number of element to read
	 * 
	 * @return number of valid elements
	 */
	public int getNumber();

	/**
	 * Gets the actual read position
	 * 
	 * @return index of the read position
	 */
	public int getOffset();

	/**
	 * Gets the overwrite status, if true we have a ring buffer, if false we
	 * have a fifo buffer
	 * 
	 * @return overwrite status
	 */
	public boolean getOverwrite();

	/**
	 * Displays the content of the ring buffer
	 * 
	 * @return a formatted string, which contains the content of the buffer
	 */
	public String toString();
}// End of interface IBuffer
