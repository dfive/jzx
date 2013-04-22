package pt.ist.ave.jzx.instructions;

public class EI extends Instruction {

	public EI(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_iff1a(1);
		_cpu.setM_iff1b(1);
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
