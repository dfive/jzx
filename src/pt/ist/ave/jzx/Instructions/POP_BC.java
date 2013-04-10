package pt.ist.ave.jzx.Instructions;

public class POP_BC extends Instruction {

	public POP_BC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.bc16(_cpu.pop16());
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
