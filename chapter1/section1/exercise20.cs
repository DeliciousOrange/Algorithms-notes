/*
*Write a recursive static method that computes the value of ln(N!)
*/
class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(ln(1000));
            // Console.WriteLine(Math.Log(factorial(5)));

            Console.Read();
        }

        private static double ln(long n)
        {
            if (n == 1)
            {
                return 0;
            }

            return Math.Log(n) + ln(n - 1);
        }
        
//另一种实现方法，先计算阶乘，然后调用Math.Log函数
        private static long factorial(int n)
        {
            if (n == 1)
            {
                return 1;
            }
            else
            {
                return n * factorial(n - 1);
            }
        }
    }