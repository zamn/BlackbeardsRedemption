package com.bbr.resource;

public class Tuple<L, R> {
	public final L left;
	public final R right;

	public Tuple(L left, R right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Tuple<?,?>) {
			Tuple<?,?> other = (Tuple<?,?>)o;
			// avoids NullPointerException for when this.left or this.right are null
			boolean leftEqual = (this.left == other.left) || (this.left != null && this.left.equals(other.left));
			boolean rightEqual = (this.right == other.right) || (this.right != null && this.right.equals(other.right));
			return leftEqual && rightEqual;
		}
		return false;
	}

	@Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 37 + left.hashCode();
        hash = hash * 37 + right.hashCode();
        return hash;
    }
}