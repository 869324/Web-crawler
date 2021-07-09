class Account {

    private long balance = 0;

    public synchronized boolean withdraw(long amount) {
        boolean value = false;
        if (this.balance > amount){
            this.balance -= amount;
            value = true;
        }
        return value;
    }

    public synchronized void deposit(long amount) {
        // do something useful
        this.balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}