package data;

public class SingleDimensionalMotif {

	private Sequence _motif;
	private int _stream;
	private int _locations;
	//this is used as a building block for the multidimensional motifs by 
	public SingleDimensionalMotif(Sequence motif, int stream) {
		_motif = motif;
		_stream = stream;
	}
	
	public int getStream()
	{
		return _stream;
	}
	
	public Sequence getTimeSeries()
	{
		return _motif;
	}
	
	public boolean overLappingMotif(SingleDimensionalMotif other)
	{
		boolean overlap = true;
		if(_stream != other._stream){
			overlap = false;
		}else{
			if(other._motif.start() >= _motif.end()){
				overlap = false;
			}else if(_motif.start() >= other._motif.end()){
				overlap = false;
			}else{
				//overlap
				overlap = true;
			}
		}
		return overlap;
	}
	
	public long start(){
		return _motif.start();
	}
	
	public long end(){
		return _motif.end();
	}
	
	
}
