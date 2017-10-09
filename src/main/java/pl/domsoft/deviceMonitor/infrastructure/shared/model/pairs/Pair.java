package pl.domsoft.deviceMonitor.infrastructure.shared.model.pairs;

/**
 * Created by szymo on 27.07.2017.
 */
public class Pair<TLeft, TRight> {
    public final TLeft left;
    public final TRight right;

    public Pair(TLeft left, TRight right) {
        this.left = left;
        this.right = right;
    }

    public TLeft getLeft() {
        return left;
    }

    public TRight getRight() {
        return right;
    }
}
