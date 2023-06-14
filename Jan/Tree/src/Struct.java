import java.util.Objects;

public class Struct<A, B    > {
    public final A one;
    public final B two;

    public Struct(A first, B second) {

        this.two = second;
        this.one = first;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Struct)) {
            return false;
        }
        Struct<?, ?> p = (Struct<?, ?>) o;
        return Objects.equals(p.one, one) && Objects.equals(p.two, two);
    }
    @Override
    public int hashCode() {
        return  (two == null ? 0 : two.hashCode()) ^  (one == null ? 0 : one.hashCode());
    }


}