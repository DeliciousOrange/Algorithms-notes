/*
*Write a code fragment that puts the binary representation of a positive integer N
*into a String s.
*/
class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(toBinaryString(7));
            Console.WriteLine("Excepted: 111");
            Console.Read();
        }

        public static string toBinaryString(int n)
        {
            string result = "";
            for (int i = n; i > 0; i/=2)
            {
                result = (i % 2) + result;
            }
            return result;
        }

//以下是while版本，可以看出while循环版不用额外定义循环变量，此外循环变量可以在循环体结束之后使用，这是它和for循环的一个重要区别。
        public static string toBinaryString(int n)
        {
            string result = "";
            while(n > 0)
            {
                result = (n % 2) + result;
                n /= 2;
            }           
            return result;
        }

    }