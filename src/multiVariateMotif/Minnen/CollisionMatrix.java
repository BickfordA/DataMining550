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
		int count = _collisions[j][i];
		return count;
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
		int count = _collisions[j][i];
		_collisions[j][i] = count + 1;
//		System.out.println("increment : " + count );
	}

	public ArrayList<CollisionPair> getMaxCollisions() {
		
		ArrayList<CollisionPair> collisions = new ArrayList<CollisionPair>();
		
		int max = 1;
		for(int i = 1 ; i < _collisions.length-1; i ++){
			for(int j  = 0; j < i ; j++){
				int collisionValue = _collisions[i][j];
				if(collisionValue > max){
					collisions.clear();
					collisions.add(new CollisionPair(i , j));
					max = collisionValue;
				}
				else if(collisionValue == max){
					collisions.add(new CollisionPair(i , j));
				}
			}
		}
		System.out.println("max collison size: " + max + "\n");
		
		return collisions;
	}
	
	public ArrayList<CollisionPair> getCollisionsGreaterThen(int size) {
		
		ArrayList<CollisionPair> collisions = new ArrayList<CollisionPair>();
		
		int max = size;
		for(int i = 1 ; i < _collisions.length-1; i ++){
			for(int j  = 0; j < i ; j++){
				int collisionValue = _collisions[i][j];
				if(collisionValue >= max){
					collisions.add(new CollisionPair(i , j));
				}
			}
		}
		System.out.println("size collison size: " + max + "\n");
		
		return collisions;
	}
	
	public int getMaxCollisionNumber()
	{
		int max = 1;
		for(int i = 1 ; i < _collisions.length-1; i ++){
			for(int j  = 0; j < i ; j++){
				int collisionValue = _collisions[i][j];
				if(collisionValue > max){
					max = collisionValue;
				}
//				System.out.print("c v "+ collisionValue);
			}
		}
		System.out.println("get Max v: " + max);
		return max;
	}

}
