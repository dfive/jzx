package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class INC_BC extends Instruction {

	public INC_BC(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {

		_cpu.inc16bc();
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 6;
	}

}
