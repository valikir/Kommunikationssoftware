import java.util.Arrays;

public class BufferGen implements IBufferGen {

    /** Minimum number of elements in buffer */
    private final int MIN_LENGTH = 5;
    /** Maximum number of elements in buffer */
    private final int MAX_LENGTH = 100;
    /** Actual length - defined by the user */
    private int length = 0;
    /** Start position to get (read) data */
    private int offset = 0;
    /** Number of elements in buffer */
    private int number = 0;
    /**
     * FiFo buffer (overwrite = false) or Ring buffer (overwrite = true)
     */
    private boolean overwrite = false;
    /** An integer buffer */
    private Object[] b = null;
    /** First value overwritten (for Ring buffer) */
    private boolean firstOverwritten = false;

    public BufferGen(int length, boolean overwrite) throws IndexOutOfBoundsException {
        this.overwrite = overwrite;
        this.length = length;
        if (this.length >= MIN_LENGTH && this.length <= MAX_LENGTH) {
            this.b = new Object[length];
        } else {
            throw new IndexOutOfBoundsException("Length must be bigger than " + MIN_LENGTH + " and smaller than " + MAX_LENGTH + "!");
        }
    }

    @Override
    public String toString() {
        Object buff[] = new Object[number];
        for (int i = 0; i < number; i++) {
            buff[i] = b[i];
        }
        return Arrays.toString(buff);
    }

    @Override
    public void add(Object value) {
        if (!overwrite) {
            if (number < length) {
                this.b[number] = value;
                number++;
            } else {
                throw new IndexOutOfBoundsException("Array is full");
            }
        } else {
            if (number < length) {
                this.b[number] = value;
                number++;
            } else {
                this.b[0] = value;
                offset = 1;
                firstOverwritten = true;
            }
        }
    }

    @Override
    public Object get() {
        if (b != null || b[0] != null) {
            if (offset < number) {
                offset++;
            } else if (offset == number && firstOverwritten) {
                return b[0];
            }
        } else {
            throw new NullPointerException("Array is empty");
        }
        return b[offset-1];
    }


    @Override
    public int getLength() {
        return b.length;
    }

    @Override
    public int getNumber() {
        if (number == 0){
            return 0;
        }
        return number-1;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public boolean getOverwrite() {
        if (overwrite){
            return true;
        }
        return false;
    }
}

