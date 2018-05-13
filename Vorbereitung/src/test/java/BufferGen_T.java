import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BufferGen_T {

        @Test(expected = IndexOutOfBoundsException.class)
        public void whenArrayFullThenThrowException() {
            BufferGen buffer = new BufferGen(10,false);
        /* Add 10 elements */
            for (int i = 1; i<=11; i++) {
                buffer.add("Hi");
            }
        }

    @Test
    public void whenAddFiveGetLengthFive() {
        BufferGen buffer = new BufferGen(5,false);
        /* Add 5 elements */
        for (int i = 1; i<=5; i++) {
            buffer.add(i);
        }
        /* Length of array is 5 */
        assertThat(buffer.getLength(), is(5));

    }
    @Test
    public void whenAddFiveGetFive() {
        BufferGen buffer = new BufferGen(5,false);
        /* Add 5 elements */
        for (int i = 1; i<=5; i++) {
            String k = "Number" + i;
            buffer.add(k); 
        }

        /* Length of array is 5 */
        assertThat(buffer.get(), is("Number1"));
        assertThat(buffer.get(), is("Number2"));
        assertThat(buffer.get(), is("Number3"));
        assertThat(buffer.get(), is("Number4"));
        assertThat(buffer.get(), is("Number5"));
    }
    @Test
    public void showGetNumberAndGetOffset() {
        BufferGen buffer = new BufferGen(5,false);
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
        BufferGen buffer = new BufferGen(5,false);
        /* Add 3 elements */
        for (int i = 1; i<=3; i++) {
            buffer.add(i);
        }
        assertThat(buffer.toString(),is("[1, 2, 3]"));

    }
    @Test
    public void whenFIFOThenOverwriteFalse() {
        BufferGen buffer = new BufferGen(5,false);
        assertThat(buffer.getOverwrite(),is(false));
    }
    @Test
    public void whenRINGLengthFiveAddSixElementsFirstOverwritten() {
        BufferGen buffer = new BufferGen(5,true);
        for (int i = 1; i<=6; i++) {
            double k = i + 0.15;
            buffer.add(k);
        }
        assertThat(buffer.getOverwrite(),is(true));
        assertThat(buffer.get(),is(2.15));
        assertThat(buffer.get(),is(3.15));
        assertThat(buffer.get(),is(4.15));
        assertThat(buffer.get(),is(5.15));
        assertThat(buffer.get(),is(6.15));
    }
}
