package pt.ist.ave.jzx.instructions;


public class DI extends Instruction {

	public DI(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_iff1a(0);
		_cpu.setM_iff1b(0);
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
