class LinkedList {
    //9920102054 MIHIR SAINI E2
    //Q2
    private Node head;
    private Node tail;
    private Node start;
    private int size;
    public LinkedList() {
        this.size = 0;
    }

    public class Node {
        private int value;
        private Node next;
        char name;
        char author;
        int bn;
        int year;
        int copies;
        char studentName;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(char name, char author, int bn, int year, int copies) {
            this.name = name;
            this.author = author;
            this.bn = bn;
            this.year = year;
            this.copies = copies;
        }

        public Node(char name, char studentName) {
            this.name = name;
            this.studentName = studentName;
        }
    }

    public void addition(Node current) {
        Node temp = head;
        if (head == null) {
            head = current;
            head.next = null;
        } else {
            current.next = null;
            temp.next = current;
            temp = temp.next;
        }
    }

    public void insertion(Node current) {
        Node temp = head;
        if (start == null) {
            start = current;
            start.next = null;
        } else {
            current.next = null;
            temp.next = current;
            temp = temp.next;
        }
    }

    public void traversal() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.name);
            System.out.println(temp.author);
            System.out.println(temp.bn);
            System.out.println(temp.year);
            System.out.println(temp.copies);
        }
    }

    public void traverseThroughStudent() {
        Node temp = start;
        while (temp != null) {
            System.out.println(temp.name);
            System.out.println(temp.studentName);
        }
    }

    public void deletion(int bookNumber) {
        Node temp = head;
        Node current = head;
        while (temp != null) {
            if (current.bn == bookNumber && current == head) {
                head = head.next;
                break;
            } else if (current.bn == bookNumber && current.next == null) {
                temp.next = null;
                break;
            } else if (current.bn == bookNumber) {
                temp.next = current.next;
                break;
            }
            temp = current;
            current = current.next;
        }
    }

    public void deleteStudent(char studentName) {
        Node current=head;
        Node temp=head;
        while(temp!=null){
            if(current.studentName==studentName&&current==start){
                start=start.next;
                break;
        }
            else if(current.studentName==studentName&&current.next==null){
                temp.next=null;
                break;
            }
            temp=current;
            current=current.next;}
    }
}
//        {
//        if(current1->student_name==d&&current1==start)
//        {
//        start=start->next;free(current1);
//        break;
//        }
//        elseif(current1->student_name==d&&current1->next==NULL)
//        {
//        temp1->next=NULL;
//        free(current1);break;
//        }
//        elseif(current1->student_name==d)
//        {
//        temp1->next=current1->next;free(current1);
//        break;
//        AKANKSHA SARASWAT 20102120 23
//        }
//        temp1=current1;current1=current1->next;
//        }
//
//    }
//}
////        {
//        head=head->next;
//        free(current);break;
//        }
//        elseif(current->isbn==d&&current->next==NULL)
//        {
//        temp->next=NULL;
//        free(current);break;
//        }
//        elseif(current->isbn==d)
//        {
//        temp->next=current->next;free(current);
//        break;
//        }
//        temp=current;current=current->next;
////        }
//
//        }
//
//
//}

//        voidtraversal()
//        {
//        temp=head;
//        while(temp!=NULL)
//        {
//        cout<<temp->name<<"
//        "<<temp->author<<"
//        "<<temp->isbn<<" "<<temp
//        ->year<<""<<temp->copies<<endl;
//        temp=temp->next;
//        }
//        }
//        voidtraversal_student()
//        {
//        temp1=start;
//        while(temp1!=NULL)
//        {
//        cout<<temp1->book<<""<<temp1->student_name<<endl;temp1=temp1->next;
//        }
//        }
//        voiddeletion()
//        {
//        intd;temp=head;current=head;
//        cout<<"Entertheisbnofthebookyouwanttoremovenode"<<endl;
//        cin>>d;while(temp!=NULL)
//        {
//        AKANKSHA SARASWAT 20102120 22
//        if(current->isbn==d&&current==head)
//        {
//        head=head->next;
//        free(current);break;
//        }
//        elseif(current->isbn==d&&current->next==NULL)
//        {
//        temp->next=NULL;
//        free(current);break;
//        }
//        elseif(current->isbn==d)
//        {
//        temp->next=current->next;free(current);
//        break;
//        }
//        temp=current;current=current->next;
//        }
//        }
//        voiddeletion_student()
//        {
//        chard[25];temp1=start;current1=start;
//        cout<<"Enterthenameofthestudentwho'snodeyouwanttoremove"<<endl;
//        cin>>d;while(temp1!=NULL)
//        {
//        if(current1->student_name==d&&current1==start)
//        {
//        start=start->next;free(current1);
//        break;
//        }
//        elseif(current1->student_name==d&&current1->next==NULL)
//        {
//        temp1->next=NULL;
//        free(current1);break;
//        }
//        elseif(current1->student_name==d)
//        {
//        temp1->next=current1->next;free(current1);
//        break;
//        AKANKSHA SARASWAT 20102120 23
//        }
//        temp1=current1;current1=current1->next;
//        }
//        }
//        intmain()
//        {
//        for(inti=1;i<=3;i++)
//        {
//        addition();
//        }
//        traversal();deletion();
//        traversal();
//        for(inti=1;i<=3;i++)
//        {
//        insertion();
//        }
//        traversal_student();
//        deletion_student();
//        traversal_student();
//        }
//        }