
public class Node {
	int jobId;
	int jobTime;
	Node next;
	
public Node(int x,int y){
	jobId=x;
	jobTime=y;
}
	
	public Node() {
		next=null;
}

	public void getNext() {
		if (this.next==null) System.out.print("null");
		else System.out.print(this.next.jobId);		
	}
}
//
