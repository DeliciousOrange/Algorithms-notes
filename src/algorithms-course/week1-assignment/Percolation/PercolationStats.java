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

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    //1.96属于魔法数字且多次出现，故应定义成字面量
    private static final double CONFIDENCE_95 = 1.96;   //  z value
    private final int count;                            //  trials

    /*
     *  这里一开始并没有想到定义实例变量mean和deviation，每次在需要它们的值时都需要调用
     *  相对应的方法，按理说并没有什么问题，但是提交作业之后给的评估报告显示我的类型设计对
     *  StdStats.mean和StdStats.stddev的调用次数为3，而标准答案是1次。这说明设计的不
     *  够简洁。到底什么原因呢？考虑用test client对我的类型的mean、confidenceLo和
     *  confidenceHi方法进行测试，那么仅仅为了获取平均值mean，就需要调用3次mean()方法，
     *  也就相应的调用3次StdStats.mean方法，对stddev方法的测试类似。这就是问题根因。
     *  那么怎么解决这个问题呢？很简单，不是将平均值作为各个方法的局部信息(局部变量)，而是
     *  将它作为类的一个实例变量或者说状态，这样各个方法就能共享该数据了。仔细想下，这是一个
     *  很合理的想法：整个类都是围绕统计信息进行设计的，将平均值和标准差设计为实例变量并在构
     *  造函数中初始化合情合理，其他方法直接拿算好的状态数据进行计算就好了，不必每次都调用方
     *  法再次重复计算。
     */
    private final double mean;                          //  sample data mean
    private final double deviation;                     //  sample data deviation

    /*
     *  Gets the percolation thresholds array through trials percolation
     *  experiments on an n-by-n grid of sites.
     */
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException(
                    "arguments out of bounds: [" + n + "," + trials + "]");
        }
        count = trials;
        double[] thresholds = new double[count];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            thresholds[i] = p.numberOfOpenSites() / (double) (n * n);
        }

        //这里一开始并没有使用提供的计算平均值和方差的库函数，谨记不要重复造轮子，除了业余学习之外
        mean = StdStats.mean(thresholds);
        if (count == 1) {
            deviation = Double.NaN;
        }
        else {
            deviation = StdStats.stddev(thresholds);
        }
    }

    /*
     *  Returns the sample mean of percolation thresholds.
     */
    public double mean() {
        return mean;
    }

    /*
     *  Returns the standard deviation of percolation thresholds.
     */
    public double stddev() {
        return deviation;
    }

    /*
     *  Returns low endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * deviation / Math.sqrt(count);
    }

    /*
     *  Returns high endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * deviation / Math.sqrt(count);
    }

    /*
     *  A test client.
     */
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),
                                                  Integer.parseInt(args[1]));
        System.out.println(p.mean());
        System.out.println(p.stddev());
        System.out.println(p.confidenceLo());
        System.out.println(p.confidenceHi());
    }

}
