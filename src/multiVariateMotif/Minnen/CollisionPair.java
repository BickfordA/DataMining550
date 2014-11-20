package multiVariateMotif.Minnen;

public class CollisionPair {

	private int _segmentA;
	private int _segmentB;
	
	public CollisionPair(int segmentA, int segmentB){
		_segmentA = segmentA;
		_segmentB = segmentB;
	}
	
	public int getA(){ return _segmentA;}
	public int getB(){ return _segmentB;}
}
