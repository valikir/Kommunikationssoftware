import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Buffer_T {
    @Test (expected = IndexOutOfBoundsException.class)
    public void whenArrayFullThenThrowException() {
        Buffer buffer = new Buffer(10,false);
        /* Add 10 elements */
        for (int i = 1; i<=11; i++) {
            buffer.add(2);
        }
    }

    @Test
    public void whenAddFiveGetLengthFive() {
        Buffer buffer = new Buffer(5,false);
        /* Add 5 elements */
        for (int i = 1; i<=5; i++) {
            buffer.add(i);
        }
        /* Length of array is 5 */
        assertThat(buffer.getLength(), is(5));

    }
    @Test
    public void whenAddFiveGetFive() {
        Buffer buffer = new Buffer(5,false);
        /* Add 5 elements */
        for (int i = 1; i<=5; i++) {
            buffer.add(i);
        }

        /* Length of array is 5 */
        assertThat(buffer.get(), is(1));
        assertThat(buffer.get(), is(2));
        assertThat(buffer.get(), is(3));
        assertThat(buffer.get(), is(4));
        assertThat(buffer.get(), is(5));
    }
    @Test
    public void showGetNumberAndGetOffset() {
        Buffer buffer = new Buffer(5,false);
        /* Add 3 elements */
        for (int i = 1; i<=3; i++) {
            buffer.add(i);
        }
        /* Length of array is 5 */
        assertThat(buffer.getNumber(), is(2));
        assertThat(buffer.getOffset(), is(0));

        buffer.get();
        assertThat(buffer.getOffset(), is(1));
    }
    @Test
    public void whenAddThreeShowThree() {
        Buffer buffer = new Buffer(5,false);
        /* Add 3 elements */
        for (int i = 1; i<=3; i++) {
            buffer.add(i);
        }
        assertThat(buffer.toString(),is("[1, 2, 3]"));

    }
    @Test
    public void whenFIFOThenOverwriteFalse() {
        Buffer buffer = new Buffer(5,false);
        assertThat(buffer.getOverwrite(),is(false));
    }
    @Test
    public void whenRINGLengthFiveAddSixElementsFirstOverwritten() {
        Buffer buffer = new Buffer(5,true);
        for (int i = 1; i<=6; i++) {
            buffer.add(i);
        }
        assertThat(buffer.getOverwrite(),is(true));
        assertThat(buffer.get(),is(2));
        assertThat(buffer.get(),is(3));
        assertThat(buffer.get(),is(4));
        assertThat(buffer.get(),is(5));
        assertThat(buffer.get(),is(6));
    }
}
