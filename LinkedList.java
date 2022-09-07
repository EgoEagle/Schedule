
public class LinkedList {
	Node listHead;

	
public LinkedList(){
	listHead=null;
}
	
public void insert(Node x){
	Node spot=listHead;
	if((listHead==null)||(listHead.jobTime>=x.jobTime)){
		x.next=listHead;
		listHead=x;
	}
	else {
	while((spot.next!=null)&&(spot.next.jobTime<x.jobTime)){
		spot=spot.next;
	}
		x.next=spot.next;
		spot.next=x;
	}
}
//
public Node dequeue() {
	Node node = null;
	if(listHead.next!=null){
		node=listHead.next;
		listHead.next=listHead.next.next;
	}
	return node;
}

public boolean isEmpty() {
	if(listHead.next==null) return true;
	else return false;
}
}
