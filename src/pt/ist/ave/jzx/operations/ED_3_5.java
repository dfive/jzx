package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class ED_3_5 extends Operation {

	private int _work8;
	
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_3,
				Z80.FLAG_5
		};
	}
	public void update5_3(int work8){
		_work8 = work8;
		
//		_cpu.setM_3F(getM_3F());
//		_cpu.setM_5F(getM_5F());
		
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
		notImplementedError("getM_parityoverflowF");
		return false;
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
		return (_work8 & Z80.ONE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_work8 & Z80.THREE_MASK) != 0;
	}

}
