package pathfinder.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pathfinder.graphadt.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MinPriorityQueueTest {

    MinPriorityQueue<Node> mpq;

    @BeforeEach
    public void setMinPriorityQueue() {
        mpq = new MinPriorityQueue<>();
    }

    @Test
    public void enqueueToQueue() {
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        mpq.enqueue(n1, 5d);
        mpq.enqueue(n2, 8d);
        mpq.enqueue(n3, 2d);


        assertEquals(mpq.size(), 3);

    }

    @Test
    public void dequeueFromQueue() {
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        mpq.enqueue(n1, 5d);
        mpq.enqueue(n2, 8d);
        mpq.enqueue(n3, 2d);


        assertEquals(mpq.dequeue(), n3);
        assertEquals(mpq.dequeue(), n1);
        assertEquals(mpq.dequeue(), n2);

    }

    @Test
    public void emptyQueue() {
        Node n1 = new Node();
        mpq.enqueue(n1, 5d);

        assertEquals(mpq.dequeue(), n1);
        assertEquals(mpq.size(), 0);
        assertNull(mpq.dequeue());
    }

}
