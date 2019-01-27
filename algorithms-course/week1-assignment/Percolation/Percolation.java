/******************************************************************************
 *  Name:   LianYing Zhang
 *  NetID:  lianying168000
 *  Precept:    P00
 *
 *  Description:    This is a Abstract Data Type for modeling a percolation
 *  system.
 *
 *  Written:    26/01/2019
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] states;   //  store sites state

    //实例变量size由于仅在构造函数中初始化，在别的地方未做更改，而仅仅是读取，故应用final修饰
    private final int size;           //  gird size

    private int count;          //  opened sites count

    //和size同理，uf除了在构造函数初始化之外，在别的地方引用并未改动，故应使用final修饰
    private final WeightedQuickUnionUF uf;    // union-find data structure

    // union-find data structure without virtual bottom site
    private final WeightedQuickUnionUF ufNoVirtualBottomSite;

    /*
     *   Initializes an empty union-find data structure with n*n+2 sites, 0
     *   through n*n+1 with n*n site is a virtual top site and n*n+1 is a
     *   virtural bottom site. Each site is initially in its component.
     */
    public Percolation(int n) {
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufNoVirtualBottomSite = new WeightedQuickUnionUF(n * n + 1);
        if (n <= 0) {
            throw new IllegalArgumentException("grid size n smaller than 0");
        }
        
        size = n;

        //若一个抽象数据类型存在一个bool实例变量数组，则在构造函数不必使用for循环进行false值的初始化，直接在使用new初始化即可
        states = new boolean[n * n];
    }

    /*
     *  Open site which locates (row,col) coordinate.
     */
    //该方法可考虑重构，可读性不够好
    public void open(int row, int col) {
        validate(row, col);
        if (!states[xyTo1D(row, col)]) {
            states[xyTo1D(row, col)] = true;
            count++;
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                ufNoVirtualBottomSite.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if ((col + 1 < size + 1) && isOpen(row, col + 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                ufNoVirtualBottomSite.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (row - 1 > 0 && isOpen(row - 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                ufNoVirtualBottomSite.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if ((row + 1 < size + 1) && isOpen(row + 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                ufNoVirtualBottomSite.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (row == 1 && isOpen(row, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(size + 1, 1));
                ufNoVirtualBottomSite.union(xyTo1D(row, col), xyTo1D(size + 1, 1));
            }

            if (row == size && isOpen(row, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(size + 1, 2));
            }
        }
    }

    /*
     *  Returns true if the site which locates at (row, col) is open.
     */
    //由于多个地方均需对(row,col)进行验证，故可考虑封装成方法，避免到处复制粘贴代码
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return states[xyTo1D(row, col)];
    }

    /*
     *   Returns true if the site which locates at (row, col) can be connected
     *   to an open site in the top row.
     */
    //使用不包含虚拟底部site的ufNoVirtualBottomSite数据结构进行full site的判定，可消除backwash问题
    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufNoVirtualBottomSite.connected(xyTo1D(row, col), xyTo1D(size + 1, 1));
    }

    /*
     *   Returns the number of open sites.
     */
    public int numberOfOpenSites() {
        return count;
    }

    /*
     *  Returns true if (size, size+1) site and (size, size+2) site is
     *  connected.
     */
    //使用包含虚拟顶部site和虚拟底部site的uf进行渗透性的判定
    public boolean percolates() {
        return uf.connected(xyTo1D(size + 1, 1), xyTo1D(size + 1, 2));
    }

    /*
     *  Returns 1-dimensional union find object index which maps from a
     *  2-dimensional (row,column) pair.
     */
    private int xyTo1D(int x, int y) {
        return size * (x - 1) + y - 1;
    }

    /*
     *  Validate row and column.
     */
    //validate验证函数的签名不一定要写成返回boolean，这样在调用点易造成多层if嵌套，可以写成void类型，避免多层if嵌套。
    private void validate(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IllegalArgumentException("row index is illegal.");
        }
        if (col <= 0 || col > size) {
            throw new IllegalArgumentException("column index is illegal.");
        }
    }

    /*
     *  A test client
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(4);

        p.open(1, 5);
        p.open(2, 1);
        p.open(2, 3);
        p.open(3, 2);
        p.open(2, 2);
        p.open(4, 2);

        System.out.println(p.isOpen(1, 2));
        System.out.println(p.isFull(3, 2));
        System.out.println(p.isFull(3, 3));
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());

    }
}
