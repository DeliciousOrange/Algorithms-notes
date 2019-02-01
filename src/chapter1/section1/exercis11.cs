/*
*Write a code fragment that prints the contents of a two-dimensional boolean 
*array, using * to represent true and a space to represent false. Include row
*and column numbers.
*/
class Program
    {
        static void Main(string[] args)
        {
            bool[,] bools = new bool[2, 2] {{true, false}, {true, false}};
            printArray(bools);
            Console.Read();
        }

        public static void printArray(bool[,] bools)
        {
            for (int row = 0; row < bools.GetLength(0); row++)
            {
                for (int column = 0; column < bools.GetLength(1); column++)
                {
                    if (bools[row, column] == true)
                    {
                        Console.WriteLine("row" + row + ",column" + column + "  *");
                    }
                    else
                    {
                        Console.WriteLine("row" + row + ",column" + column + " ");
                    }
                }
            }
        }
    }