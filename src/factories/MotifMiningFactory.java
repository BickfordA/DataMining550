package factories;

import multiVariateMotif.MotifMiner;
import multiVariateMotif.Balasubramanian.Balasubramanian;
import multiVariateMotif.McGovern.McGovern;
import multiVariateMotif.Minnen.Minnen;
import services.TunableParameterService;

public class MotifMiningFactory {
	
	//Create the instance of the motif mining algorithm which will be used
	
	private final MotifMiningAlgorithmEnum _algorithmType;
	
	public MotifMiningFactory()
	{
		this(TunableParameterService.getInstance());
	}
	
	public MotifMiningFactory(TunableParameterService parameterService)
	{
		_algorithmType = parameterService.getMotifAlgorithm();
	}
	
	public MotifMiner getMotifMiner() throws ClassNotFoundException
	{
		switch(_algorithmType)
		{
			case Balasubramanian:
				return new Balasubramanian();
			case McGovern:
				return new McGovern();
			case Minnen:
				return new Minnen();
			default:
				throw new ClassNotFoundException("Not a valid motif mining class name");
		}
	}

}
