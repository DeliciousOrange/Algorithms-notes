# Percolation

> 目的：实现Percolation模型、用蒙特卡罗模拟估计渗滤阀值的值。

这个编程作业的目的是要让我们实现Percolation抽象数据类型的公共API。该ADT对实际生活中常见的渗透问题进行了建模。在实现时，类型内部要用到作者封装好的WeightedQuickUnionUF抽象数据类型（对Union-Find算法的一种实现）。

该题目的一个难点就是如何解决backwash(回流)问题。若按课程上所说使用一个虚拟顶部site和一个虚拟底部site且仅用一个WeightedQuickUnionUF数据结构来降低当输入变大时的耗时问题，那么就会出现backwash问题：即使最底层的site不是full site，而只是open site也会因为和虚拟底部 site相连，而导致误判为full site，从而导致在使用PercolationVisualizer.java绘图时，也当成full site进行绘图。

解决这个问题的一个方法是另外使用一个仅包含虚拟顶部site的WeightedQuickUnionUF数据结构，该数据结构仅用于Percolation数据类型的isFull方法。

另外，课程提供的lift-java.pkg安装包中包含Spotbugs和Checkstyle插件，能够按照课程已定义好的规则对编程者的代码规范度进行检查。

最后，我在作答此题目时犯的一些规范问题和编程bug均在源码中进行记载，此处不再详细说明。

