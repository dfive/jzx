package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class CPD extends Operation {

	private int _bc16;

	{	
		_updatedFlags = new int[]{
				Z80.FLAG_PARITY_OVERFLOW,
		};
	}
	
	public void cpd() {
		_bc16 = _cpu.bc16();
		
//		_cpu.setM_parityoverflowF(getM_parityoverflowF());
		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		notImplementedError("getM_addsubtractF");
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return _bc16 != 0;
	}

	@Override
	public boolean getM_halfcarryF() {
		notImplementedError("getM_halfcarryF");
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		notImplementedError("getM_zeroF");
		return false;
	}

	@Override
	public boolean getM_signF() {
		notImplementedError("getM_signF");
		return false;
	}

	@Override
	public boolean getM_5F() {
		notImplementedError("getM_5F");
		return false;
	}

	@Override
	public boolean getM_3F() {
		notImplementedError("getM_3F");
		return false;
	}

}
