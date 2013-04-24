package pt.ist.ave.jzx.operations;

public class DefaultOperation extends Operation {

	/**
	 * the default is to return false on all flags
	 * that is, all flags are set to zero whehn the Z80 starts.
	 */
	
	@Override
	public boolean getM_carryF() {
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		return false;
	}

	@Override
	public boolean getM_signF() {
		return false;
	}

	@Override
	public boolean getM_5F() {
		return false;
	}

	@Override
	public boolean getM_3F() {
		return false;
	}

}
