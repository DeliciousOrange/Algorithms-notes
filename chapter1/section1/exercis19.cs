/*
*What is the largest value of N for which this program takes less 1 hour to compute
*the value of F(N)? Develop a better implementation of F(N) that saves computed values
*in an array.
*/
class Program
    {
        static void Main(string[] args)
        {
            for (int i = 0; i < 90; i++)
            {
                long[] arr;

                if (i == 0 || i == 1)
                {
                    arr = new long[2];
                }
                else
                {
                    arr = new long[i + 1];
                }

                arr[0] = 0;
                arr[1] = 1;

                Console.WriteLine(i + "  " + F(i, arr));
            }
            Console.Read();
        }
//时间复杂度比较高的版本
        public static long F(int n)
        {
            int[] arr = new int[n];
            if (n == 0)
            {
                return 0;
            }

            if (n == 1)
            {
                return 1;
            }

            return F(n - 1) + F(n - 2);
        }
//以下版本通过开辟额外的空间，即用数组保存已经计算过的值，来降低时间复杂度
        public static long F(int n, long[] arr)
        {
            if (n == 0)
            {
                return arr[0];
            }

            if (n == 1)
            {
                return arr[1];
            }

            for (int i = 2; i <= n; i++)
            {
                arr[i] = arr[i - 2] + arr[i - 1];
            }

            return arr[n];
        }
    }