package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.Z80;
import pt.ist.ave.jzx.operations.RRA_Operation;
import pt.ist.ave.jzx.operations.OperationsFactory;

public class RRA extends Instruction {

	public RRA(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
//		int work8 = (_cpu.getM_carryF() ? 1 : 0);
//		_cpu.setM_carryF((_cpu.getM_a8() & 0x01) != 0);
//		_cpu.setM_a8((_cpu.getM_a8() >> 1) |( work8 << 7));
//		_cpu.setM_halfcarryF(false);
//		_cpu.setM_addsubtractF(false);
//		_cpu.setM_3F((_cpu.getM_a8() & Z80.THREE_MASK) != 0);
//		_cpu.setM_5F((_cpu.getM_a8() & Z80.FIVE_MASK) != 0);
		RRA_Operation rraOperation = OperationsFactory.rra;
		rraOperation.rra();
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
