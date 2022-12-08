
 public class Bank {
            private int accNo;
            private double balance;
            private String customerName;
            private String email;
            private int phoneNo;
            public bank()
            {
                this(234323, 333.45, "MIHIR SAINI", "mugeer@gmail.com", 9927099040);//this is a special use of the 'this' keyword to call one constructor from another constructor and setting its default values
                System.out.println("Default or Empty constructor");

            }
            public bank(int accNo,double balance,String customerName, String email, int phoneNo)
            {
                System.out.println("Parameterized constructor");
                this.accNo=accNo; //with constructors, one doesn't have to call setters as fields are initialized within the constructor
                this.balance=balance;
                this.customerName=customerName;
                this.phoneNo=phoneNo;
                this.email=email;
            }
            public int getAccNo() {
                return this.accNo;
            }

            public double getBalance() {
                return this.balance;
            }

            public String getCustomerName() {
                return this.customerName;
            }

            public String getEmail() {
                return this.email;
            }

            public int getPhoneNo() {
                return this.phoneNo;
            }

            public void setAccNo(int accNo) {
                this.accNo = accNo;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setPhoneNo(int phoneNo) {
                this.phoneNo = phoneNo;
            }

            public void setDeposit(double depositAmt) {
                this.balance += depositAmt;
                System.out.println(depositAmt + " added." + " Balance = " + this.balance);
            }

            public void setWithdrawal(double withdrawalAmt) {
                if (this.balance-withdrawalAmt<0) {
                    System.out.println("Insufficient balance. ");}
             else
                    this.balance -= withdrawalAmt;
                System.out.println("Withdrawal processed. Balance = " + this.balance);
            }
        }



