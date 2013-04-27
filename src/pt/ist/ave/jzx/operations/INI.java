package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class INI extends Operation {

	private int _b8;
	{
		_updatedFlags = new int[] {
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_ZERO,
		};
	}
	public void ini() { 
		
		_b8 = _cpu.getM_b8();
		
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
//		updateFlags();
		
		// TODO: handle 3F, 5F
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return true;
	}

	@Override
	public boolean getM_parityoverflowF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		return _b8 == 0;
	}

	@Override
	public boolean getM_signF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_5F() {
		//TODO
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_3F() {
		//TODO
		notImplementedError("getM_carryF");
		return false;
	}

}
