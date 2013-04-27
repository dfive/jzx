package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class DefaultOperation extends Operation {

	/**
	 * the default is to return false on all flags
	 * that is, all flags are set to zero whehn the Z80 starts.
	 */
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ZERO,
				Z80.FLAG_SIGN,
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_PARITY_OVERFLOW,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_CARRY,
				Z80.FLAG_3,
				Z80.FLAG_5
		};
//		updateFlags();
	}
	
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
