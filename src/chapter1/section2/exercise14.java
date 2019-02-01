import edu.princeton.cs.algs4.Date;

public class Transaction {
    private String customer;
    private Date date;
    private double amount;

    public Transaction(String who, Date when, double amount) {
        this.customer = who;
        this.date = when;
        this.amount = amount;
    }

    public String who() {
        return customer;
    }

    public Date when() {
        return date;
    }

    public double mount() {
        return amount;
    }

    public String toString() {
        return customer + ": " + date + " " + amount;
    }

    //重写equals方法的典型模版
    public boolean equals(Object transaction) {
        if (this == transaction) {
            return true;
        }
        if (transaction == null) {
            return false;
        }
        if (transaction.getClass() != this.getClass()) {
            return false;
        }
        Transaction trans = (Transaction) transaction;


        // 此处为了消除多重if嵌套可以改写成3个条件表达式相与，但这样条件语句过长，不易阅读，
        // 可以将每个条件表达式进行反转，每个if语句均加一个return语句。
        if (customer.equals(trans.customer)) {
            if (date.equals(trans.date)) {
                if (amount == (trans.amount)) {
                    return true;
                }
            }
        }
        return false;

        // if (this.date != trans.date) {
        //     return false;
        // }
        // if (this.customer != trans.customer) {
        //     return false;
        // }
        // if (this.amount != trans.amount) {
        //     return false;
        // }
        // return true;
    }

    public static void main(String[] args) {

    }
}
