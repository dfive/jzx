package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_DMHL extends Instruction {

	public LD_DMHL(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.hl16()));
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 7;
	}

}
