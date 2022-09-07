import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Scheduling {
	int numNodes;
	int ProcGiven;
	int totalJobTimes;
	int[][] adjacencyMatrix;
	int[][] scheduleTable;
	int[] jobTimeAry;
	LinkedList OPEN;
	int[] processJob;
	int[] processTime;
	int[] parentCount;
	int[] jobDone;
	int[] jobMarked;
	Node[] JobList;

public Scheduling(){
	OPEN=new LinkedList();
	Node dummy=new Node(0,0);
	insertOpen(dummy);
	jobDone=new int[numNodes+1];
	jobMarked=new int[numNodes+1];
	parentCount=new int[numNodes+1];
	processTime=new int[numNodes+1];
	processJob=new int[numNodes+1];
	jobTimeAry=new int[numNodes+1];
	totalJobTimes=0;

}
	
public void loadMatrix(File input) throws FileNotFoundException{
	Scanner in=new Scanner(input);
	numNodes=in.nextInt();
	parentCount=new int[numNodes+1];
	adjacencyMatrix=new int[numNodes+1][numNodes+1];
	while(in.hasNext()){
		int i=in.nextInt();
		int j=in.nextInt();
		Node job=new Node(i,j);
		parentCount[j]++;
		adjacencyMatrix[i][j]=1;
	}
	in.close();
}

public int getUnMarkOrphen(){
	int i=1;
	while(i<numNodes+1){
		if((jobMarked[i]==0) && (parentCount[i]==0)) return i;
		i++;
	}
	return -1;
}

public int computeTotalJobTimes(File input) throws FileNotFoundException{
	Scanner in=new Scanner(input);
	int skip=in.nextInt();
	jobTimeAry=new int[numNodes+1];
	int index=1;
	JobList=new Node[numNodes+1];
	while(in.hasNext()){
		int id=in.nextInt();
		int jobTime=in.nextInt();
		totalJobTimes+=jobTime;
		Node job=new Node(id,jobTime);
		JobList[index]=job;
		jobTimeAry[index]=jobTime;
		index++;
	}
	in.close();
	return totalJobTimes;
}

public void insertOpen(Node node){
	Node spot=OPEN.listHead;
	if((OPEN.listHead==null)||(OPEN.listHead.jobTime>=node.jobTime)){
		node.next=OPEN.listHead;
		OPEN.listHead=node;
	}
	else {
	while((spot.next!=null)&&(spot.next.jobTime<node.jobTime)){
		spot=spot.next;
	}
		node.next=spot.next;
		spot.next=node;
	}
}

public void printList(LinkedList OPEN){
	Node current = OPEN.listHead.next;
    System.out.print("dummyNode -->");
    while(current != null) {
        System.out.print("("+current.jobId+","+current.jobTime+",");
        current.getNext();
        System.out.print(") --> ");
        current = current.next;
    }
    if(current==null) System.out.print("(null)");
    System.out.println();
}

public void printTable(PrintWriter out){
	int i=0;
	System.out.print("     -");
	while(i!=totalJobTimes) {
		if(i>9) System.out.print(i+"--");
		else System.out.print(i+"---");
	i++;
	}
	System.out.println(totalJobTimes); 
	for(int row=1;row<ProcGiven+1;row++) {
		System.out.print("P("+row+")|");
	for(int col=0;col<numNodes+1;col++) {
		if(scheduleTable[row][col]>9)System.out.print(" "+scheduleTable[row][col]+"|");
		else if(scheduleTable[row][col]==0)System.out.print(" - |");
		else System.out.print(" "+scheduleTable[row][col]+" |");
	}
	System.out.println();
	}
}

public int findProcessor(){
	int i=1;
	while(i!=ProcGiven+1){
		if(processTime[i]<=0) {
			return i;
			}
		i++;
	}
	return -1;
}

public void updateTable(int availProc,Node newJob, int currentTime){
	int count=currentTime;
	while(count!=currentTime+newJob.jobTime){
		scheduleTable[availProc][count]=newJob.jobId;
		count++;
	}
}

public boolean checkCycle(){
	boolean firstCondit = false;
	boolean secCondit = false;
	boolean thirdCondit = true;
	if(OPEN.listHead.next==null) firstCondit=true;
	int j=1;
	while(j!=numNodes+1) {
	if(jobDone[j]!=1) secCondit=true;
	j++;
	}
	
	int i=1;
	while(i!=ProcGiven+1){
		boolean test;
	if(processTime[i]<=0) test=true;
	else test=false;
	thirdCondit=thirdCondit&&test;
	i++;
	}

	return firstCondit&&secCondit&&thirdCondit;
}

Node findDoneJob(int processor){
		if(processTime[processor]==0){
			int id=processJob[processor];
			if(id==0) return null;
			processJob[processor]=0;
			return JobList[id];
		}
		else return null;

}

void deleteNode(Node node){
	jobDone[node.jobId]=1;
}

void deleteEdge(Node node){
	int kidIndex=0;
	while(kidIndex!=numNodes){
	if(adjacencyMatrix[node.jobId][kidIndex]>0) {
		parentCount[kidIndex]--;
		adjacencyMatrix[node.jobId][kidIndex]=0;
	}
	kidIndex++;
	}
}

public void getProc() {
	Scanner in=new Scanner(System.in);
	System.out.println("How many processors?");
	int proc=in.nextInt();
	if(proc<=0) System.out.print("Processor needs to get greater than 0");
	if(proc>=numNodes) ProcGiven=numNodes;
	else ProcGiven=proc;
	in.close();
}

public void run(File inputFile, File inputFile2, PrintWriter out) throws FileNotFoundException {
	
	loadMatrix(inputFile);
	totalJobTimes=computeTotalJobTimes(inputFile2);	
	getProc();
	
	
	jobDone=new int[numNodes+1];
	jobMarked=new int[numNodes+1];
	processTime=new int[ProcGiven+1];
	processJob=new int[numNodes+1];
	scheduleTable=new int[numNodes+1][numNodes+1];
	
	int currentTime=0;
	while(!AllDone()) {
	
	while(getUnMarkOrphen()!=-1){  
	int orphenNode=getUnMarkOrphen();
	if(orphenNode!=-1) {
	jobMarked[orphenNode]=1;
	Node node=new Node(orphenNode,jobTimeAry[orphenNode]);
	insertOpen(node);	
	}
}
	printList(OPEN);
	
	int ProcUsed=0;
	int availProc=0;
	while(!OPEN.isEmpty()&&(availProc!=-1)) {
	availProc=findProcessor();
	//System.out.println(availProc);
	if(availProc>0){
		ProcUsed++;
		Node newJob=OPEN.dequeue();
		processJob[availProc]=newJob.jobId;
		processTime[availProc]=newJob.jobTime;
		updateTable(availProc,newJob,currentTime);
	}
	}
	if(checkCycle()) {
		System.out.print("Error, there is a cycle");
		return;
	}
	printTable(out);
	
	currentTime++;
	int i=1;
	while(i!=ProcGiven+1) {
		if(processTime[i]>0)
		processTime[i]--;
		i++;
	}
	for(int o=1;o<ProcGiven+1;o++) {
	Node job=findDoneJob(o);
	if(job!=null) {
	deleteNode(job);
	deleteEdge(job);
	}
	}
	
	System.out.println("current time: "+currentTime);
	System.out.print("jobMarked: ");
	for(int a=1;a<numNodes+1;a++) {
		System.out.print(jobMarked[a]+" "); 
	}
	System.out.println();
	System.out.print("processTime: ");
	for(int a=1;a<ProcGiven+1;a++) {
		System.out.print(processTime[a]+" "); 
	}
	System.out.println();
	System.out.print("processJob: ");
	for(int a=1;a<numNodes+1;a++) {
		System.out.print(processJob[a]+" "); 
	}
	System.out.println();
	System.out.print("jobDone: ");
	for(int a=1;a<numNodes+1;a++) {
		System.out.print(jobDone[a]+" ");
	}
	System.out.println();

	}
	printTable(out);
	
}

private boolean AllDone() {
	boolean total=true;
	for(int i=1;i<numNodes+1;i++) {
		if(jobDone[i]!=1) total=(total&&false);
	}
	return total;
}

void printADJ() {
	for(int i=0;i<numNodes+1;i++) {
		for(int j=0;j<numNodes+1;j++) {
			System.out.print(adjacencyMatrix[i][j]);
		}
		System.out.println();
	}
}
}

//

