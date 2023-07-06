public class Linked_List {
    private static class Node {
        private final int key;
        private Node next;
        private Node prev;

        public Node(int key) {
            this.key = key;
            next = prev = null;
        }

        public Node(int key, Node next, Node prev) {
            this.key = key;
            this.next = next;
            this.prev = prev;
        }

        public int getKey() {
            return key;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    private final Node head;

    public Linked_List() {
        head = new Node(Integer.MIN_VALUE);
        head.setNext(head);
        head.setPrev(head);
    }

    public void list_insert(int x) {
        Node n = new Node(x, head.getNext(), head);
        head.getNext().setPrev(n);
        head.setNext(n);
    }

    private Node list_search(int k) {
        Node x = head.getNext();
        while (x != head && x.getKey() != k)
            x = x.getNext();
        return x;
    }

    private void list_delete(Node x) {
        x.getPrev().setNext(x.getNext());
        x.getNext().setPrev(x.getPrev());
    }

    public void search(int k) {
        Node x = list_search(k);
        if (x == head) System.out.println(k + " not found");
        else System.out.println(k + " found");
    }

    public void delete(int k) {
        Node x = list_search(k);
        if (x != head)
            list_delete(x);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node x = head.getNext();
        sb.append("[ ");
        while (x != head) {
            sb.append(x.getKey());
            sb.append(", ");
            x = x.getNext();
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" ]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Linked_List L = new Linked_List();
        L.list_insert(3);
        L.list_insert(8);
        L.list_insert(7);
        L.list_insert(1);
        L.list_insert(6);
        L.list_insert(0);

        System.out.println(L);
        L.search(8);
        L.search(80);
        L.delete(8);
        System.out.println(L);
    }
}
