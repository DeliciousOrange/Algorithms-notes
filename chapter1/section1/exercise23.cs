/*
*Write a version of BinarySearch that uses the recursive rank() given on page 25 and
*traces the method calls. Each time the recursive method is called, print the argument
*values lo and hi, indented by the depth of the recursion. Hint: Add an argument to the
*recursive method that keeps track of the depth.
*/
class Program
    {
        static void Main(string[] args)
        {
            int[] arr=new int[7] {1,2,3,5,4,6,7};
            int key = 2;

            //注意要先排序
            Array.Sort(arr);

            int index = BinarySearch(2, arr);

            Console.WriteLine();
            Console.WriteLine(index);
            //这里增添一个index变量存储BinarySearch返回的值，是为了能够在前面打印一行空行
            //Console.WriteLine(BinarySearch(2, arr));
            Console.WriteLine("Expected: 3");
            Console.Read();
        }

        public static int BinarySearch(int key, int[] a)
        {
            int depth = 0;
            return rank(key, a, 0, a.Length - 1, depth);
        }

        public static int rank(int key, int[] a, int low, int high, int depth)
        {
            if (low>high)
            {
                return -1;
            }

            if (depth!=0)
            {
                Console.WriteLine();
            }

            while (depth > 0)
            {
                Console.Write("  ");
                depth--;
            }
            
            Console.WriteLine("low={0:},high={1}",low, high);

            int mid = (low + high) / 2;

            if (key<a[mid])
            {
                depth++;
                return rank(key, a, low, mid-1, depth); 
            }
            else if (key>a[mid])
            {
                depth++;
                return rank(key, a, mid+1, high, depth);
            }
            else
            {
                return mid;
            }
        }
    }