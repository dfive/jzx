package pt.ist.ave.jzx.Instructions;

import pt.ist.ave.jzx.Z80;

public class RLA extends Instruction {

	public RLA(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		int work8 = (_cpu.getM_carryF() ? 1 : 0);
		_cpu.setM_carryF(((_cpu.getM_a8() & 0x80) != 0));
		_cpu.setM_a8(((_cpu.getM_a8() << 1) | work8) & 0xff);
		_cpu.setM_halfcarryF(false);
		_cpu.setM_addsubtractF(false);
		_cpu.setM_3F((_cpu.getM_a8() & Z80.THREE_MASK) != 0);
		_cpu.setM_5F((_cpu.getM_a8() & Z80.FIVE_MASK) != 0);
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
