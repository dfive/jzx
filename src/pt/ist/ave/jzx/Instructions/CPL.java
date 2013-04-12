package pt.ist.ave.jzx.Instructions;

import pt.ist.ave.jzx.Z80;

public class CPL extends Instruction {

	public CPL(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_a8() ^ 0xff);
		_cpu.setM_halfcarryF(true);
		_cpu.m_addsubtractF = true;
		_cpu.setM_3F((_cpu.getM_a8() & Z80.THREE_MASK) != 0);
		_cpu.setM_5F((_cpu.getM_a8() & Z80.FIVE_MASK) != 0);
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
