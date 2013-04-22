package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_EMHL extends Instruction {

	public LD_EMHL(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.hl16()));
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 7;
	}

}
