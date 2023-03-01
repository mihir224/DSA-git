import java.util.ArrayList;
import java.util.List;

public class LinkedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode bottom;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //reverse linked list
    //https://leetcode.com/problems/reverse-linked-list/

    //recursive
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) { //iterating till head is at the end
            return head;
        }
        ListNode dummy = reverseList(head.next);
        //switching directions
        head.next.next = head;
        head.next = null;
        return dummy;
    }

    //iterative
    public ListNode reverseList2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev = null;
        ListNode present = head;
        ListNode next = present.next;
        while (present != null) {
            present.next = prev;
            prev = present;
            present = next;
            if (next != null) {
                next = next.next;
            }

        }
        return prev;
    }

    //middle of linked list
    //https://leetcode.com/problems/middle-of-the-linked-list/
    //fast-slow pointer
    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //merge two sorted lists
    //https://leetcode.com/problems/merge-two-sorted-lists

    //storing ans in a new list (O(n) space)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode();
        ListNode temp = head;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                temp.next = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        temp.next = list1 != null ? list1 : list2;
        return head.next;
    }

    //in-place - Create an ans node pointing to list 1 which will store our ans list.
    // Always point list 1 to the smaller val among the two - list 1 and list 2. Then move list1 till it is smaller than list 2.
    //Before moving list1 to the next node, point a temp node to the current node of list 1 to keep track of the last smallest node.
    // Once list 1 node val becomes greater than list 2 node, point the temp ie point the last smallest node to list2 node, then
    //swap list1 and 2. Repeat till any of the list becomes null, then return the ans node.
    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val > list2.val) { //point list 1 to smaller val
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }
        ListNode ans = list1;
        while (list1 != null && list2 != null) {
            ListNode temp = null;
            while (list1 != null && list1.val <= list2.val) {
                temp = list1;
                list1 = list1.next;
            }
            //list2 is smaller than list1, thus change direction and swap
            temp.next = list2; //change direction of the last smallest node to whatever list2 is pointing to
            //swap
            ListNode tmp = list1;
            list1 = list2;
            list2 = tmp;
        }
        return ans;
    }

    //remove nth node from the end
    //https://leetcode.com/problems/remove-nth-node-from-end-of-list/

    //brute force, applying the logic of removing a node at any index
    //(O(n+n=2n)) TC, space optimization much better than the other soln
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode temp = head;
        int size = 0;
        if (head == null || head.next == null) {
            return null;
        }
        while (temp != null) { //O(n)
            size++;
            temp = temp.next;
        }
        //first element is to be removed
        if (n == size) { //O(n)
            head = head.next;
            return head;
        }
        //last element is to be removed
        if (n == 1) { //O(n)
            ListNode p = get(head, size - 2);
            p.next = null;
            return head;
        }
        //O(n)
        ListNode prev = get(head, size - n - 1); //element previous to nth element from the last
        prev.next = prev.next.next;
        return head;
    }

    public ListNode get(ListNode head, int index) {
        ListNode node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    //fast and slow pointer approach - Point both slow and fast to head. Move fast by n times. Then move both fast and slow till fast's next becomes null
    //then point slow.next to slow.next.next. Edge case: when we wish to dlt the first node. In this case after n steps
    //fast will reach the last node and its next will be null. Thus, the case we will never be able to move the slow pointer
    //so, in this case also we simply point slow.next to slow.next.next
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode temp = new ListNode();
        temp.next = head;
        ListNode fast = temp;
        ListNode slow = temp;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return temp.next;
    }

    //add two numbers
    //https://leetcode.com/problems/add-two-numbers/

    //only one solution which is optimal
    //edge cases - two lists have different lengths, two lists have same length

    //create a dummy node, point a temp node to it. Initialize carry as 0 and iterate over both lists, adding the values of current l1
    // and l2 to a temp sum variable. If there's a carry, add it to carry variable and then create a new node which stores
    // the ones place value of the sum which contains the carry and point temp's next to that node (This will work in both cases - carry and no carry).
    // Then move temp. Iteration is done till list 1 and 2 become null and the carry becomes zero. Then return dummy.next which will be our ans list
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode temp = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = 0;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            sum += carry;
            carry = sum / 10;
            temp.next = new ListNode(sum % 10);
            temp = temp.next;
        }
        return dummy.next;
    }

    //delete node in linked list (no head given)
    //https://leetcode.com/problems/delete-node-in-a-linked-list/
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    //intersection (optimal - O(n+m))
    //https://leetcode.com/problems/intersection-of-two-linked-lists/

    //brute (n*m) - traverse both nodes simultaneously and break if a node matches

    //better (n+m)tc, o(n+m)space - traverse both, add nodes in a map if not already there. If a node already exists, return that node

    //optimal o(n+m)tc, o(1)space - take 2 dummy nodes pointing to head1 and 2. Then move both by n and m times.
    // If any dummy becomes null, point it to the head of other list. Iterate till both dummies become equal.
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode temp1 = headA;
        ListNode temp2 = headB;
        while (temp1 != temp2) {
            temp1 = temp1 == null ? headB : temp1.next;
            temp2 = temp2 == null ? headA : temp2.next;
        }
        return temp1;
    }

    //cycle detection
    //https://leetcode.com/problems/linked-list-cycle/
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    //reverse nodes in k-grp
    //https://leetcode.com/problems/reverse-nodes-in-k-group/
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k < 1 || head == null) {
            return head;
        }
        int length = getLength(head);
        int numGrps = length / k;
        ListNode prev = null;
        ListNode current = head;
        while (numGrps > 0) {
            ListNode next = current.next;
            ListNode temp = prev;
            ListNode newEnd = current;
            for (int i = 0; current != null && i < k; i++) {
                current.next = prev;
                prev = current;
                current = next;
                if (next != null) {
                    next = next.next;
                }
            }
            if (temp != null) {
                temp.next = prev;
            } else {
                head = prev;
            }
            newEnd.next = current;
            prev = newEnd;
            numGrps--;
        }
        return head;
    }

    public int getLength(ListNode head) {
        ListNode temp = head;
        int count = 0;
        while (temp != null) {
            temp = temp.next;
            count++;
        }
        return count;
    }

    //palindrome linked list
    //https://leetcode.com/problems/palindrome-linked-list/
    public boolean isPalindrome(ListNode head) {
        ListNode mid = middleNode(head);
        ListNode midHead = reverseList(mid);
        ListNode rereverse = midHead;
        while (head != null && midHead != null) {
            if (head.val != midHead.val) //not a palindrome
                break;
            head = head.next;
            midHead = midHead.next;
        }
        reverseList(rereverse);
        return (head == null) || (midHead == null); //when either of the 2 lists were completely traversed
    }

    //starting node of cycle
    //https://leetcode.com/problems/linked-list-cycle-ii/
    //the hasCycle method only tells whether cycle exists or not. It is not necessary that fast and slow will always
    // meet at the starting point of the node, so we implement following approach to find starting node of cycle:
    ///find length of cycle. Take two pointers, move the second till length of cycle. Then move both till they meet
    public ListNode detectCycle(ListNode head) {
        int length = getCycleLength(head);
        if (length == 0) {
            return null;
        }
        ListNode first = head;
        ListNode second = head;
        while (length > 0) {
            second = second.next;
            length--;
        }
        while (first != second) {
            first = first.next;
            second = second.next;
        }
        return first;
    }

    public int getCycleLength(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        int length = 0;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                ListNode temp = slow;
                do {
                    temp = temp.next;
                    length++;
                } while (temp != slow);
                return length;
            }
        }
        return 0;
    }

    //flattening of linked list
    //https://practice.geeksforgeeks.org/problems/flattening-a-linked-list/1

    //O(total nodes)

    //recursively move till the last node, then while coming back, merge the last two nodes in each call
    ListNode flatten(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode lastNode = flatten(head.next);
        ListNode mergedHead = mergeTwoLists3(head, lastNode);
        return mergedHead;
    }

    public ListNode mergeTwoLists3(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val > list2.val) { //point list 1 to smaller val
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }
        ListNode ans = list1;
        while (list1 != null && list2 != null) {
            ListNode temp = null;
            while (list1 != null && list1.val <= list2.val) {
                temp = list1;
                list1 = list1.bottom;
            }
            //list2 is smaller than list1, thus change direction and swap
            temp.bottom = list2; //change direction of the last smallest node to whatever list2 is pointing to
            //swap
            ListNode tmp = list1;
            list1 = list2;
            list2 = tmp;
        }
        return ans;
    }

    //rotate list
    //https://leetcode.com/problems/rotate-list/description/

    //brute force (O(n*k))
    // iterate k times and in each iteration find the last node, point it to head, then point its prev node to null and make the last node head

    //optimal (O(n))
    //find length, then find last, point it to head, then iterate till length-rotations-1 (to find the last element after k rotations)
    //then make the new last.next as head, and point the new last to null

    public ListNode rotateRight(ListNode head, int k) {
        int length=1;
        if(head==null||head.next==null||k<=0){
            return head;
        }
        ListNode lastNode=head;
        while(lastNode.next!=null){
            lastNode=lastNode.next;
            length++;
        }
        lastNode.next=head;
        int rotations=k%length; //in case k>length
        ListNode newLast=head;
        for(int i=0;i<length-rotations-1;i++){
            newLast=newLast.next;
        }
        head=newLast.next;
        newLast.next=null;
        return head;
    }
}
