package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_AMBC extends Instruction {

	public LD_AMBC(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.bc16()));
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 7;
	}

}
