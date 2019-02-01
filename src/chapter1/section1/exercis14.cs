/*
*Write a static method lg() that takes an int value N as argument 
*and returns the largest int not larger than the base-2 logarithm of N. Do not use Math.
*/
class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(lg(7));
            Console.WriteLine("Except: 2");
            Console.Read();
        }

        public static int lg(int n)
        {
            int result = 0;
            while (n > 1)
            {
                result++;
                n /= 2;
            }
            return result;
        }
    }