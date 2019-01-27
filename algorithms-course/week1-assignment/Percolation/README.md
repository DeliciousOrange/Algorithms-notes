# Percolation

> 目的：实现Percolation模型、用蒙特卡罗模拟估计渗滤阀值的值。

这个编程作业的目的是要让我们实现Percolation抽象数据类型的公共API。该ADT对实际生活中常见的渗透问题进行了建模。在实现时，类型内部要用到作者封装好的WeightedQuickUnionUF抽象数据类型（对Union-Find算法的一种实现）。

该题目的一个难点就是如何解决backwash(回流)问题。