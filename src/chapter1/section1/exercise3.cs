/*
*Write a program that takes three integer command-line arguments and prints
*equal if all three are equal, and not equal otherwise.
*/
class Program
    {
        static void Main(string[] args)
        {
                try
                {
                    int num1 = int.Parse(args[0]);
                    int num2 = int.Parse(args[1]);
                    int num3 = int.Parse(args[2]);
                    if (num1==num2 && num2==num3)
                    {
                        Console.WriteLine("equal"); 
                    }
                    else
                    {
                        Console.WriteLine("not equal");
                    }
                }
                catch (FormatException e)
                {
                    Console.WriteLine("请输入有效的数字");
                }                                    
        }
    }