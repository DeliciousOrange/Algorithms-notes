/**
*Write a static method histogram() that takes an array a[] of int values 
*and an integer M as arguments and returns an array of length M whose ith
*entry is the num- ber of times the integer i appeared in the argument array.
*If the values in a[] are all between 0 and M–1, the sum of the values in the
*returned array should be equal to a.length.
*/
class Program
    {
        static void Main(string[] args)
        {
            int[] a = { 1, 2, 2, 4 };
            int[] newArr = histogram(a, 5);
            for (int i = 0; i < newArr.Length; i++)
            {
                Console.Write(newArr[i] + " ");
            }
            Console.WriteLine();
            Console.WriteLine("Expect: 0 1 2 0 1");
            Console.Read();
        }

        public static int[] histogram(int[]a, int m)
        {
            int[] newArr=new int[m];
            for (int i = 0; i < a.Length; i++)
            {
                if (a[i] < m)
                {
                    newArr[a[i]]++;
                }
            }
            return newArr;

        }
    }