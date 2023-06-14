import java.util.Objects;

public class Error {
    Integer fp;
    Integer fn;

    public Error(Integer fp, Integer fn) {
        this.fp = fp;
        this.fn = fn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(fp, error.fp) && Objects.equals(fn, error.fn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fp, fn);
    }

    public Error substract(Error leftError) {
        return new Error(this.fp - leftError.fp , this.fn - leftError.fn);
    }

    public Error add(Error rightError) {
        return new Error(this.fp+rightError.fp , this.fn+rightError.fn);
    }
}
