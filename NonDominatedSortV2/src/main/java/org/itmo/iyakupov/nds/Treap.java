package org.itmo.iyakupov.nds;

public class Treap {
	public static class Treaps {
		Treap l, r;
		public Treaps() {};
		public Treaps(Treap l, Treap r) {
			super();
			this.l = l;
			this.r = r;
		}
	}
	
	public Int2DIndividual x;
	public int y;

	public Treap left;
	public Treap right;    

	public Treap(Int2DIndividual x, int y, Treap left, Treap right) {
		this.x = x;
		this.y = y;
		this.left = left;
		this.right = right;
	}

	public static Treap merge(Treap l, Treap r) {
	    if (l == null) return r;
	    if (r == null) return l;

	    if (l.y > r.y) {
	        Treap newR = merge(l.right, r);
	        return new Treap(l.x, l.y, l.left, newR);
	    } else {
	        Treap newL = merge(l, r.left);
	        return new Treap(r.x, r.y, newL, r.right);
	    }
	}
	
	public Treaps splitX(Int2DIndividual x) {
		Treaps res = new Treaps();
		Treaps t = null;
	    if (this.x.compareX1(x) <= 0) {
	        if (right == null) {
	            res.r = null;
	        } else {
	        	t = right.splitX(x);
	        	res.r = t.r;
	        }
	        res.l = new Treap(this.x, y, left, t.l);
	    } else {
	        if (left == null) {
	            res.l = null;
	        } else {
	        	t = left.splitX(x);
	        }
	        res.r = new Treap(this.x, y, t.r, right);
	    }
	    return res;
	}
	
	/**
	 * same as split X - members of r have greater x1 than members of l
	 * @param x
	 * @return
	 */
	public Treaps splitY(Int2DIndividual x) {
		Treaps res = new Treaps();
		Treaps t = null;
	    if (this.x.compareX2(x) > 0) {
	        if (right == null) {
	            res.r = null;
	        } else {
	        	t = right.splitX(x);
	        	res.r = t.r;
	        }
	        res.l = new Treap(this.x, y, left, t.l);
	    } else {
	        if (left == null) {
	            res.l = null;
	        } else {
	        	t = left.splitX(x);
	        }
	        res.r = new Treap(this.x, y, t.r, right);
	    }
	    return res;
	}
	
	public boolean dominatesSomebody(Int2DIndividual nInd) {
		Treaps split = splitX(nInd);
		if (split.l != null && nInd.compareDom(split.l.x) < 0)
			return true;
		if (split.r != null && nInd.compareDom(split.r.x) < 0)
			return true;
		return false;
	}

	public boolean dominatedBySomebody(Int2DIndividual nInd) {
		Treaps split = splitX(nInd);
		if (split.l != null && nInd.compareDom(split.l.x) > 0)
			return true;
		if (split.r != null && nInd.compareDom(split.r.x) > 0)
			return true;
		return false;
	}
	
	public Int2DIndividual getMinP() {
		Treap l = this;
		while (l.left != null) 
			l = l.left;
		Treap r = this;
		while (r.right != null)
			r = r.right;
		return new Int2DIndividual(l.x.getX1(), r.x.getX2());
	}
}