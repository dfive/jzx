package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class DAA_Operation extends Operation {

	private int m_a8;
	private boolean addsubtract;
	private boolean carry;

	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_PARITY_OVERFLOW,
				Z80.FLAG_CARRY,
		};
	}
	
	public void daa(boolean addsubtract, boolean carry) {
		this.addsubtract = addsubtract;
		this.carry = carry;
		m_a8 = _cpu.getM_a8();

		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		return carry;
	}

	@Override
	public boolean getM_addsubtractF() {
		return addsubtract;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return Z80.m_parityTable[m_a8];
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
