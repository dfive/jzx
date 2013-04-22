package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class INC_SP extends Instruction {

	public INC_SP(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		_cpu.inc16sp();
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 6;
	}

}
