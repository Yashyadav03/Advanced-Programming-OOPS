package org.example;

import java.util.*;

class Book
{
    private static int nextBookId=1;
    private int bookId;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public long timeborrow=0;

    public Book (String title, String author, int totalCopies)
    {
        this.bookId=nextBookId++;
        this.title=title;
        this.author=author;
        this.totalCopies=totalCopies;
        this.availableCopies=totalCopies;
        this.timeborrow=0;
    }

    public int getBookId ()
    {
        return bookId;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getAuthor ()
    {
        return author;
    }

    public int getTotalCopies ()
    {
        return totalCopies;
    }

    public int getAvailableCopies ()
    {
        return availableCopies;
    }

    public void decreaseAvailableCopies()
    {
        availableCopies--;
    }

    public void increaseAvailableCopies ()
    {
        availableCopies++;
    }

    public long gettimeborrow()
    {
        return timeborrow;
    }
    public void settimeborrow(long timeb)
    {
        this.timeborrow=timeb;
    }

    public String toString()
    {
        return this.bookId+" "+this.title+" By - "+this.author+" Total Copies - "+this.totalCopies+" Available Copies - "+this.availableCopies;
    }
}

class Student
{
    private static int nextStudentId=1;
    private String name;
    private int age;
    private String phonenumber;
    private int balance;
    private List<Integer> BorrowedBooks;
    private int StudentId;

    public Student(String name, int age, String phonenumber)
    {
        this.name=name;
        this.age=age;
        this.phonenumber=phonenumber;
        this.balance=0;
        this.BorrowedBooks= new ArrayList<>();
        this.StudentId=nextStudentId++;
    }

    public String getname()
    {
        return name;
    }
    public int getage()
    {
        return age;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public int getBalance()
    {
        return balance;
    }

    public int getStudentId()
    {
        return StudentId;
    }

    public List<Integer> getBorrowedBooks()
    {
        return BorrowedBooks;
    }


    public void borrowBook (int bookId)
    {
        if (balance > 0)
        {
            System.out.print ("Issue Action Failed! First clear the penalty amount of "+balance+" Rupees.");
        }
        else
        {
            BorrowedBooks.add(bookId);
        }
    }

    public void returnbook (int bookId)
    {
        BorrowedBooks.remove(Integer.valueOf(bookId));
    }

    public void payDues ()
    {
        balance=0;
    }

    public void accumulateFine (int amount)
    {
        balance=balance+amount;
    }

    public String tostring()
    {
        return StudentId+ " "+ name+ " Age - " +age +"Phone - "+phonenumber+" Balance- "+ balance;
    }
}

class Library
{
    private List<Book> books;
    private List<Student> students;
    private Student loggedInStudent;
    private static int nextinteger=1;
    private int studId;

    public Library ()
    {
        books=new ArrayList<>();
        students = new ArrayList<>();
        this.studId=nextinteger++;
    }

    public int getStudentId()
    {
        return studId;
    }

    public int checkbalance()
    {
        if (loggedInStudent.getBalance()==0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    public void listborrowedbooks()
    {
        if (loggedInStudent.getBorrowedBooks().equals(null))
        {
            System.out.println("No books Found.");
        }
        else {
            for (int a : loggedInStudent.getBorrowedBooks()) {
                for (Book book : books) {
                    if (book.getBookId() == a) {
                        System.out.println("Book ID: " + a);
                        System.out.println("Name: " + book.getTitle());
                        System.out.println("Author: " + book.getAuthor() + "\n");

                    }
                }
            }
        }
    }


    public int getbalance ()
    {
        return loggedInStudent.getBalance();
    }

    public void addbook (String title, String author, int totalcopies)
    {
        books.add (new Book(title, author, totalcopies));
        System.out.println ("Book Added Successfully!");
        this.studId=nextinteger++;
    }

    public void removebook (int bookId)
    {
        Book bookToRemove=findBookById (bookId);
        if (bookToRemove==null)
        {
            System.out.println ("Book Not Found.");
        }
        else
        {
            books.remove(bookToRemove);
            System.out.println ("Book removed successfully!");
        }
    }

    public void registerMember (String name, int age, String phoneNumber)
    {
        students.add (new Student(name, age, phoneNumber));
        System.out.println ("Member Registered Successfully with "+ studId +"!");
    }

    public void removeMember (int studentId)
    {
        Student studentToRemove=findstudentById (studentId);
        if (studentToRemove ==null)
        {
            System.out.println ("Member Not Found.");
        }
        else
        {
            students.remove(studentToRemove);
            System.out.println ("Member with ID "+studentId+" removed successfully!");
        }
    }

    public void loginAsStudent (String name, String phoneNumber)
    {
        loggedInStudent = findStudentByPhoneNumber (phoneNumber);
        if (loggedInStudent==null)
        {
            System.out.print ("Member with Name: "+name+" and Phone No: "+phoneNumber+" doesn't exist.");
        }
    }

    public void issueBook (int bookId)
    {
        Book bookToIssue = findBookById (bookId);
        if (bookToIssue.getAvailableCopies()<=0)
        {
            System.out.print("No available copies of this book.");
        }
        else
        {
            loggedInStudent.borrowBook(bookId);
            bookToIssue.decreaseAvailableCopies();

            bookToIssue.settimeborrow((System.currentTimeMillis())/1000);
            System.out.println("Book issued successfully!");
        }
    }

    public void returnBook (int bookID)
    {
        Book bookToReturn=findBookById(bookID);
        if (bookToReturn!=null && loggedInStudent.getBorrowedBooks().contains(bookID))
        {
            long c=(System.currentTimeMillis()/1000-bookToReturn.gettimeborrow()-10);
            int z=(int)c;
            int fine =z*3;
            bookToReturn.increaseAvailableCopies();
            if (fine>0)
            {
                loggedInStudent.accumulateFine(fine);
                System.out.println ("Book ID: "+bookID+" successfully returned. "+z*3+" Rupees has been charged for a delay of "+z+" days.");
            }
            else{
                loggedInStudent.returnbook(bookID);
                bookToReturn.increaseAvailableCopies();
                System.out.println ("Book returned successfully");
            }
        }
        else
        {
            System.out.println ("Book not found or not borrowed by this Student.");
        }
    }

    public void payfine () {
        if (loggedInStudent.getBalance() == 0) {
            System.out.println("No fine was found.");
        } else {
            System.out.println("You had a total fine of Rs. " + loggedInStudent.getBalance() + ". It has been paid successfully!");
            loggedInStudent.payDues();
        }
    }
    public void listBooks()
    {
        System.out.println ("List of available books: ");
        for (Book book : books)
        {
            System.out.println("BookID - "+book.getBookId());
            System.out.println("Name - "+book.getTitle());
            System.out.println("Author - "+book.getAuthor());
            System.out.println("Copies - "+book.getAvailableCopies());
        }
    }

    public List<Student> getstudents()
    {
        return students;
    }

    public void listStudents()
    {
        System.out.println ("List of registered students: ");
        for (Student student : students)
        {
            System.out.println("Member ID: "+student.getStudentId());
            System.out.println("Name: "+student.getname());
            loginAsStudent(student.getname(),student.getPhonenumber());
            listborrowedbooks();
            for (int a : student.getBorrowedBooks())
            {
                for (Book book : books)
                {
                    if (a==book.getBookId())
                    {
                        long x=System.currentTimeMillis()/1000-book.gettimeborrow();
                        int b=(int)x;
                        if (b>10)
                        {
                            student.accumulateFine((b-10)*3);
                        }
                    }
                }
            }
            System.out.println("Balance: "+student.getBalance());
            student.payDues();
        }
    }

    public Book findBookById (int bookId)
    {
        for (Book book : books)
        {
            if (book.getBookId()==bookId)
            {
                return book;
            }
        }
        return null;
    }

    public Student findstudentById (int studentId)
    {
        for (Student student : students)
        {
            if (student.getStudentId()==studentId)
            {
                return student;
            }
        }
        return null;
    }

    public Student findStudentByPhoneNumber (String phoneNo)
    {
        for (Student student : students)
        {
            if (student.getPhonenumber().equals(phoneNo))
            {
                return student;
            }
        }
        return null;
    }
}

class Librarian
{
    public static void main (String args[])
    {
        Library library=new Library ();
        Scanner scanner=new Scanner (System.in);
        System.out.println("Library Portal Initialized....");
        int choice=0;
        while (choice!=3)
        {
            System.out.println("---------------------------------");
            System.out.println("1. Enter as a librarian");
            System.out.println("2. Enter as a member");
            System.out.println("3. Exit");
            System.out.println("---------------------------------");

            System.out.print("Enter Choice: ");

            choice =scanner.nextInt();
            scanner.nextLine();

            if (choice==1)
            {
                int choice1 = 0;

                while (choice1 != 7)
                {
                    System.out.println("---------------------------------");
                    System.out.println("1. Register a member");
                    System.out.println("2. Remove a member");
                    System.out.println("3. Add a book");
                    System.out.println("4. Remove a book");
                    System.out.println("5. View all members along with their books and fines to be paid");
                    System.out.println("6. View all books");
                    System.out.println("7. Back");
                    System.out.println("---------------------------------");

                    System.out.print("Enter Choice: ");

                    choice1 = scanner.nextInt();
                    scanner.nextLine();

                    if (choice1==1)
                    {
                        System.out.println("---------------------------------");
                        String name;
                        System.out.print ("Name: ");
                        name=scanner.nextLine();

                        int age;
                        System.out.print ("Age: ");
                        age=scanner.nextInt();
                        scanner.nextLine();

                        String phone;
                        System.out.print ("Phone no: ");
                        phone=scanner.nextLine();

                        System.out.println("---------------------------------");

                        library.registerMember(name, age, phone);

                    } else if (choice1==2)
                    {
                        System.out.println("---------------------------------");
                        System.out.print("Enter member ID: ");
                        int id=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("---------------------------------");
                        library.removeMember(id);

                    } else if (choice1==3)
                    {
                        System.out.println("---------------------------------");
                        String title;
                        String Author;
                        int Copies;
                        System.out.print("1. Book title: ");
                        title=scanner.nextLine();
                        System.out.print("2. Author: ");
                        Author=scanner.nextLine();
                        System.out.print("Copies: ");
                        Copies=scanner.nextInt();
                        System.out.println("---------------------------------");

                        library.addbook(title, Author, Copies);

                    } else if (choice1==4)
                    {
                        System.out.println("---------------------------------");
                        System.out.print("Enter Book ID: ");
                        int bookId=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("---------------------------------");
                        library.removebook(bookId);

                    } else if (choice1==5)
                    {
                        library.listStudents();
                    } else if (choice1==6)
                    {
                        System.out.println("---------------------------------");
                        library.listBooks();
                    }
                    else if (choice1!=7)
                    {
                        System.out.println("Please Enter a Valid Choice.");
                    }
                }
            } else if (choice==2)
            {
                String StudentName;
                String PhoneNo;
                System.out.print("Name: ");
                StudentName=scanner.nextLine();
                System.out.print("Phone No: ");
                PhoneNo=scanner.nextLine();

                int m=0;

                for (Student student : library.getstudents())
                {
                    if (student.getPhonenumber().equals(PhoneNo))
                    {
                        m=1;
                    }
                }

                if (m==0)
                {
                    System.out.println("---------------------------------");
                    System.out.println ("Error: Member not Registered.");
                }
                else {
                    library.loginAsStudent(StudentName,PhoneNo);
                    Student stud=library.findStudentByPhoneNumber(PhoneNo);
                    System.out.println ("Welcome "+StudentName+". Member ID: "+stud.getStudentId());
                    int choice2 = 0;

                    while (choice2 != 6) {

                        System.out.println("---------------------------------");
                        System.out.println("1. List Available Books");
                        System.out.println("2. List My Books");
                        System.out.println("3. Issue book");
                        System.out.println("4. Return book");
                        System.out.println("5. Pay Fine");
                        System.out.println("6. Back");
                        System.out.println("---------------------------------");

                        System.out.print("Enter Choice: ");

                        choice2 = scanner.nextInt();
                        scanner.nextLine();

                        if (choice2 == 1) {
                            library.listBooks();
                        } else if (choice2 == 2) {
                            library.listborrowedbooks();
                        }
                         else if (choice2 == 3) {

                            if (library.checkbalance()==1)
                            {
                                System.out.println("Error: Clear the penalty money before issuing a new book.");
                            }
                            else {

                                library.listBooks();
                                int bookId;
                                System.out.print("Book ID: ");
                                bookId = scanner.nextInt();
                                scanner.nextLine();
                                String bookname;
                                System.out.print("Book Name: ");
                                bookname = scanner.nextLine();
                                System.out.println("---------------------------------");
                                library.issueBook(bookId);
                            }

                        } else if (choice2 == 4) {
                            library.listborrowedbooks();
                            int bookid;
                            System.out.print("Enter Book ID: ");
                            bookid = scanner.nextInt();
                            scanner.nextLine();
                            library.returnBook(bookid);

                        } else if (choice2 == 5) {
                            library.payfine();
                        }
                        else if (choice2!=6)
                        {
                            System.out.println("Please Enter a Valid Choice.");
                        }
                    }
                }
            }
            else if (choice==3)
            {
                System.out.print("Thanks for visiting!");
            }
            else
            {
                System.out.println("Please Enter a Valid Choice.");
            }
        }
    }
}