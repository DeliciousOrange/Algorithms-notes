# Algorithms's notes

1、iterator存在的目的不是为了替代for循环，也不是为性能而生，主要用于用抽象的手段隔离内部的实现细节。  
2、归并排序对于小的子数组开销有些大（因为需要迭代调用，这样就会创建大量小数组），实践中可行的一个改进是：当数组小于等于7时使用插入排序，运行时间大约快20%左右  
3、java的comparable API实现的是total order全序关系，简单来说，就是数组中的items能够按照特定顺序排列，全序关系是一种binary relation <=，满足三个关系式：  
+ 非对称性 如果v<=w，并且w<=v，那么v=w
+ 传递性   如果v<=w，并且w<=x,那么v<=x
+ 完整性   要么v<=w，要么w<=v，要么两者相等

值得注意的是：double类型不满足全序关系，因为Double.NaN <= Double.NaN为false。    
我们日常接触到的对自然数的标准排序、字符串的字母表排序以及对日期按年份的排序都是全序排序。

4、在java中，callback的实现是通过interface实现的。类似的，c#是通过delegate实现的，c是通过函数指针实现的，c++是通过class-type functors实现的，python和js是通过first-class functions实现的。  
5、选择排序的运行时间和数据的顺序无关，它总是需要平方的时间，即使数据是有序的，因为它总是需要遍历一遍右边剩下的未排序元素来找到最小的元素；但它需要的数据移动次数是最小的，仅仅使用线性增长次数的数据交换，每个item仅使用一次exchange就被放到了它最终应该待的有序位置。  
6、插入排序若要排序一个随机顺序的数组（keys不重复），需要大约1/4 N平方次比较和大约约1/4 N平方次交换。最好情况即数组是有序的情况下，需要N-1次比较和0次交换；最差情况下及数组是逆序的情况下，需要1/2 N平方次比较和1/2 N平方次交换。  
7、若一个数组逆序对的数量<=cN，那么，我们称它是部分有序数组，对于部分有序数组而言，插入排序运行时间是线性的，因为，交换次数等于逆序对的数量，比较次数等于交换次数+N-1。    
8、逆序对(inversion)是a pair of keys that are out of order. 比如：A E E L M O T R X P S中，有T-R T-P T-S R-P X-P X-S共6个逆序对。  
9、排序算法的目标就是将数组元素的主键按照某种方式排列，其中，元素和主键的具体性质在不同的应用中均有差别，在java中，元素通常都是对象，对主键的抽象描述则是通过Comparable接口实现的，具体来说，是该接口中的compareTo方法，该方法定义了对象的自然次序。  
10、如何实现非易变(immutable)数据类型？非易变数据类型是指一旦创建就无法改变数据类型的值的类型。它的优势有：  
+ 简化调试
+ 在有恶意代码攻击时更安全
+ 简化并发编程
+ 可以在优先级队列或符号表中作为key使用  
immutable数据类型的唯一劣势就是对于每个数据类型值必须创建一个new object。
要实现immutable，我们需要对传入构造函数的input进行copy，在data type里面创建一个private final instance variable持有这个copy，同时实例方法不改变这个私有实例变量。  
java中有许多immutable数据类型，比如String,Integer,Double,Color,Vector,Transaction,Point2D.同时也有许多Mutable数据类型，比如StringBuilder,Stack,Counter,Java array。  

11、使用equals和compareTo作为key比较的interface有什么区别？  
算法第四版的算法3.1 SequentialSearchST class使用的是equals方法，而其余的符号表的实现均使用compareTo方法作为key interface。  
我们选择两者其一的依据是我们为解决问题选择的数据结构。equals方法告诉我们两个对象是否严格相等(这是可由我们重写默认的equals方法控制的)。而comparable interface使我们得知两个元素的相对顺序，要么大于，要么小于，要么等于。  
顺序搜索意味着迭代整个元素集合，其中我们只保留那些等于某个键的元素。它们是否小于或大于键并不重要，我们只寻找那些相等的，并检查每个元素。虽然提供这种API的泛型集合可以使用compareTo()，并且只过滤与key相比等于0的元素，但是最好使用为相等测试而设计的equals方法。  
技术上来说，equals和compareTo在进行equality tests时能进行互换，但通常不这样做。  
然而，若我们使用像树Tree这样的数据结构，那么我们需要使用compareTo方法，因为元素之间的order在search和insert时是重要的。  
正是compareTo方法给出的元素之间的相对order使得我们得以避免顺序搜索。**顺序搜索就是迭代整个集合。**在使用Tree结构,不能使用equals替代compareTo方法。
