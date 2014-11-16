package factories;

import discretization.Alphabet;
import discretization.NormalAlphabet;
import services.TunableParameterService;

public class AlphabetFactory {

	private final AlphabetEnum _alphabetType;
	
	public AlphabetFactory()
	{
		this(TunableParameterService.getInstance());
	}
	
	public AlphabetFactory(TunableParameterService paramService)
	{
		_alphabetType = paramService.getAlphabet();
	}
	
	public Alphabet getAlphabet() throws ClassNotFoundException
	{
		switch(_alphabetType)
		{
		case NormalAlphabet:
			return new NormalAlphabet();
		default:
			throw new ClassNotFoundException("Not a valid alphabet class name");
		}
	}
	
}
