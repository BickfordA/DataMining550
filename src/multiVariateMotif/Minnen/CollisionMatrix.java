package multiVariateMotif.Minnen;

import java.util.ArrayList;

public class CollisionMatrix {
	
	int[][] _collisions;
	
	public CollisionMatrix(int numberOfItems) {
		//There is a mmmuch better way to do this
		//we are only  goin go  use half of the array
		_collisions = new int[numberOfItems][numberOfItems ];
	}
	
	public int getAt(int i, int j){
		if(i > j){
			int t = j;
			j = i;
			i = t;
		}
		return _collisions[i][j];
	}
	
	public void setAt(int i, int j, int value){
		if(i > j){
			int t = j;
			j = i;
			i = t;
		}
		_collisions[i][j] = value;
	}
	
	public void incrementAt(int i, int j){
		if(i > j){
			int t = j;
			j = i;
			i = t;
		}
		_collisions[i][j]++;
	}

	public ArrayList<CollisionPair> getMaxCollisions() {
		ArrayList<CollisionPair> collisions = new ArrayList<CollisionPair>();
		
		int max = 1;
		for(int i = 1 ; i < _collisions.length-1; i ++){
			for(int j  = 0; j < i ; j++){
				if(_collisions[i][j] > max){
					collisions.clear();
					collisions.add(new CollisionPair(i , j));
				}
				else if(_collisions[i][j] == max){
					collisions.add(new CollisionPair(i , j));
				}
			}
		}
		
		return collisions;
	}

}
