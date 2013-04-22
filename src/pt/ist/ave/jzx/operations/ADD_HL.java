package pt.ist.ave.jzx.operations;

public class ADD_HL extends Operation {

	private int _val16;
	
	public ADD_HL(int val16) {
		_val16 = val16;
	}

	@Override
	public boolean getM_carryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_signF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_5F() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_3F() {
		// TODO Auto-generated method stub
		return false;
	}

}
